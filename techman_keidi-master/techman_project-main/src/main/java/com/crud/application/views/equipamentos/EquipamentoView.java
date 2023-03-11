package com.crud.application.views.equipamentos;

import com.crud.application.components.appnav.UploadField;
import com.crud.application.data.entity.Equipamentos;
import com.crud.application.data.service.EquipamentosService;
import com.crud.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
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
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;
import org.springframework.util.unit.DataSize;

import javax.annotation.security.PermitAll;
import java.io.*;
import java.time.LocalDateTime;
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

    static class EquipamentosFormDialog extends Dialog {
        @Serial
        private static final long serialVersionUID = 6055099001923416653L;

        public EquipamentosFormDialog(final Equipamentos equipamentos, final EquipamentosService equipamentosService, final Consumer<Equipamentos> consumer) {
            FormLayout formLayout = new FormLayout();

            Binder<Equipamentos> binder = new Binder<>(Equipamentos.class);

            TextField txtNome = new TextField("Nome");

            TextField txtDescricao = new TextField("Descrição");

            var cbAtivo = new Checkbox("Ativo");

            final UploadField upload = new UploadField().withMaxFiles(1).withMaxFileSize((int) DataSize.ofMegabytes(25).toBytes());
            if (equipamentos.getId() == null) {
                formLayout.add(upload, 5);
            }

            binder.forField(cbAtivo)
                    .bind(Equipamentos::getAtivo, Equipamentos::setAtivo);

            binder.forField(txtNome).asRequired()
                    .withValidator(new StringLengthValidator("O nome deve ter entre 3 e 250 caracteres", 3, 250))
                    .bind(Equipamentos::getNome, Equipamentos::setNome);

            binder.forField(txtDescricao).asRequired()
                    .withValidator(new StringLengthValidator("A descrição deve ter entre 3 e 250 caracteres", 3, 250))
                    .bind(Equipamentos::getDescricao, Equipamentos::setDescricao);

            binder.setBean(equipamentos);

            formLayout.add(txtNome, txtDescricao,cbAtivo);
            add(formLayout);

            Button btnSave = new Button("Salvar", event -> {
                if (binder.writeBeanIfValid(equipamentos)) {
                    if (equipamentos.getId() == null) {
                        equipamentos.setData(LocalDateTime.now());
                    }
                    try (InputStream is = upload.getInputStream()) {
                        consumer.accept(equipamentosService.salvar(equipamentos, upload.getFileName(), is.readAllBytes()));
                        this.close();
                    } catch (IllegalArgumentException | IllegalStateException error) {
                        Notification.show(error.getMessage());
                    } catch (Exception ex) {
                        Notification.show("Erro desconhecido " + ex);
                    }
                }
            });

            Button cancelButton = new Button("Cancelar", e -> close());

            getFooter().add(btnSave);
            getFooter().add(cancelButton);

            open();
        }
    }
}
