package com.crud.application.views.equipamentos;

import com.crud.application.components.appnav.StreamToByteArrayConverter;
import com.crud.application.components.appnav.UploadField;
import com.crud.application.data.entity.Equipamentos;
import com.crud.application.data.service.EquipamentosService;
import com.crud.application.views.MainLayout;
import com.google.common.io.ByteStreams;
import com.sun.jna.StringArray;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.compress.utils.IOUtils;

import javax.annotation.security.PermitAll;
import java.io.*;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@PageTitle("Equipamentos")
@Route(value = "equipamentos", layout = MainLayout.class)
@RouteAlias(value = "none", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class EquipamentoView extends VerticalLayout {
    public EquipamentoView(EquipamentosService service) {
        Grid<Equipamentos> grid = new Grid<>();

        final GridListDataView<Equipamentos> gridListDataView = grid.setItems(service.listarTodos());
        grid.addColumn(Equipamentos::getId).setHeader("ID").setWidth("60px");
        grid.addColumn(Equipamentos::getNome).setHeader("Nome").setWidth("20%");
        grid.addColumn(Equipamentos::getDescricao).setHeader("Descrição").setWidth("40%");
        grid.addColumn(Equipamentos::getData).setHeader("Data").setWidth("20%");
        grid.addColumn(equipamentos -> equipamentos.getAtivo() ? "Ativo" : "Inativo").setHeader("Ativo").setComparator(Equipamentos::getAtivo).setWidth("10%");
        grid.addColumn(new ComponentRenderer<>(equipamentos -> {
            StreamResource resource = new StreamResource("imagem.jpg", () -> new ByteArrayInputStream(equipamentos.getImagem()));
            Image image = new Image(resource, "Descrição da Imagem");
            image.setMaxHeight("50px");
            return image;
        })).setHeader("Imagem").setResizable(true).setWidth("80px");
        grid.addComponentColumn(item -> {
            Button editButton = new Button("Editar");
            editButton.addClickListener(event -> {
                new EquipamentosFormDialog(item, service, c -> {
                    gridListDataView.addItem(c);
                    gridListDataView.refreshAll();
                });
            });
            return editButton;
        }).setHeader("Editar").setWidth("85px").setResizable(true);

        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, comentario) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> {
                        service.delete(comentario.getId());
                        UI.getCurrent().getPage().reload();
                    });
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                })).setWidth("45px").setResizable(true);

        Button btnAdicionar = new Button("Adicionar");
        btnAdicionar.addClickListener(event -> {
            // Cria uma instância do formulário
            new EquipamentosFormDialog(new Equipamentos(), service, c -> {
                gridListDataView.addItem(c);
                gridListDataView.refreshAll();
            });
        });
        add(btnAdicionar, grid);
    }
    public static String processUpload(InputStream inputStream) throws IOException {
        byte[] bytes = IOUtils.toByteArray(inputStream); // use a biblioteca Apache Commons IO para converter o InputStream em um array de bytes
        return Base64.getEncoder().encodeToString(bytes);
    }
    static class EquipamentosFormDialog extends Dialog {
        @Serial
        private static final long serialVersionUID = 6055099001923416653L;

        public EquipamentosFormDialog(final Equipamentos equipamentos, final EquipamentosService equipamentosService, final Consumer<Equipamentos> consumer) {
            FormLayout formLayout = new FormLayout();

            Binder<Equipamentos> binder = new Binder<>(Equipamentos.class);

            TextField txtNome = new TextField("Nome");
            TextField txtDescricao = new TextField("Descrição");
            DateTimePicker dateTimePicker = new DateTimePicker();

            var cbAtivo = new Checkbox("Ativo");

            UploadField uploadField = new UploadField();

            //testar muito
            binder.forField(uploadField)
                    .withConverter(new StreamToByteArrayConverter())
                    .bind(Equipamentos::getImagem, Equipamentos::setImagem);

            binder.forField(cbAtivo)
                    .bind(Equipamentos::getAtivo, Equipamentos::setAtivo);

            binder.forField(txtNome).asRequired()
                    .withValidator(new StringLengthValidator("O nome deve ter entre 3 e 250 caracteres", 3, 250))
                    .bind(Equipamentos::getNome, Equipamentos::setNome);

            dateTimePicker.setLabel("Data");
            add(dateTimePicker);

            binder.forField(dateTimePicker).asRequired()
                    .bind(Equipamentos::getData, Equipamentos::setData);


            binder.forField(txtDescricao).asRequired()
                    .withValidator(new StringLengthValidator("A descrição deve ter entre 3 e 250 caracteres", 3, 250))
                    .bind(Equipamentos::getDescricao, Equipamentos::setDescricao);


            // Define o objeto que será editado pelo formulário
            binder.setBean(equipamentos);

            // Abre o diálogo de edição do equipamentos
            formLayout.add(txtNome, txtDescricao, dateTimePicker, uploadField,cbAtivo);
            add(formLayout);

            // Configura o diálogo para salvar o objeto Cliente quando o botão 'Salvar' for clicado
            Button btnSalvar = new Button("Salvar", evento -> {
                if (binder.writeBeanIfValid(equipamentos)) {
                    consumer.accept(equipamentos);
                    equipamentosService.salvar(equipamentos);
                    close();
                } else {
                    Notification.show("Preencha todos os campos corretamente.");
                }
            });

            Button cancelButton = new Button("Cancelar", e -> close());

            getFooter().add(btnSalvar);
            getFooter().add(cancelButton);

            // Abre o diálogo
            open();
        }
    }
}
