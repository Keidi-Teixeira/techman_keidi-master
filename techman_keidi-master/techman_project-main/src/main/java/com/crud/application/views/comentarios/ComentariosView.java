package com.crud.application.views.comentarios;

import com.crud.application.data.entity.Comentarios;
import com.crud.application.data.entity.Equipamentos;
import com.crud.application.data.entity.User;
import com.crud.application.data.service.ComentariosService;
import com.crud.application.data.service.EquipamentosService;
import com.crud.application.data.service.UserService;
import com.crud.application.views.MainLayout;
import com.crud.application.views.equipamentos.EquipamentoView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
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

import javax.annotation.security.PermitAll;
import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

@PageTitle("Comentarios")
@Route(value = "comentarios", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class ComentariosView extends VerticalLayout {
        public ComentariosView(ComentariosService service, EquipamentosService equipamentosService, UserService userService) {
            Grid<Comentarios> grid = new Grid<>();
            final GridListDataView<Comentarios> gridListDataView = grid.setItems(service.listarTodos());
            grid.addColumn(Comentarios::getId).setHeader("ID").setResizable(true).setWidth("40px");
            grid.addColumn(c -> c.getEquipamento().getNome()).setHeader("Equipamento").setResizable(true).setWidth("70px");
            grid.addColumn(Comentarios::getComentario).setHeader("Comentário").setResizable(true).setWidth("150px");
            grid.addColumn(Comentarios::getDataComentario).setHeader("Data").setResizable(true).setWidth("50px");

            grid.addComponentColumn(item -> {
                Button editButton = new Button("Editar");
                editButton.addClickListener(event -> {
                    new com.crud.application.views.comentarios.ComentariosView.ComentariosFormDialog(item, userService,equipamentosService , service, c -> {
                        gridListDataView.addItem(c);
                        gridListDataView.refreshAll();
                    });
                });
                return editButton;
            }).setHeader("Editar").setWidth("85px");

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
                    })).setClassNameGenerator(item -> "align-left");

            Button btnAdicionar = new Button("Adicionar");
            btnAdicionar.addClickListener(event -> {
                // Cria uma instância do formulário
                new com.crud.application.views.comentarios.ComentariosView.ComentariosFormDialog(new Comentarios(),userService ,equipamentosService, service, c -> {
                    gridListDataView.addItem(c);
                    gridListDataView.refreshAll();
                });
            });
            add(btnAdicionar, grid);
        }

        static class ComentariosFormDialog extends Dialog {
            @Serial
            private static final long serialVersionUID = 6055099001923416653L;

            public ComentariosFormDialog(final Comentarios comentarios, UserService userService, EquipamentosService equipamentosService , final ComentariosService comentariosService, final Consumer<Comentarios> consumer) {
                FormLayout formLayout = new FormLayout();

                Binder<Comentarios> binder = new Binder<>(Comentarios.class);
                // Cria os campos de texto do formulário
                TextField txtNome = new TextField("Comentário");

                final ComboBox<Equipamentos> cbEquipamentos = new ComboBox<>("Equipamentos");
                cbEquipamentos.setItems(equipamentosService.listarTodos());
                cbEquipamentos.setItemLabelGenerator(Equipamentos::getNome);

                binder.forField(cbEquipamentos).asRequired()
                        .bind(Comentarios::getEquipamento, Comentarios::setEquipamento);

                final ComboBox<User> cbUser = new ComboBox<>("Usuários");
                cbUser.setItems(userService.listarTodos());
                cbUser.setItemLabelGenerator(User::getNome);

                binder.forField(cbUser).asRequired()
                        .bind(Comentarios::getUsuario, Comentarios::setUsuario);

                binder.forField(txtNome).asRequired()
                        .withValidator(new StringLengthValidator("O comentário deve ter entre 3 e 250 caracteres", 3, 50))
                        .bind(Comentarios::getComentario, Comentarios::setComentario);

                binder.setBean(comentarios);

                // Abre o diálogo de edição do equipamentos
                formLayout.add(txtNome, cbEquipamentos, cbUser);
                add(formLayout);

                // Configura o diálogo para salvar o objeto Cliente quando o botão 'Salvar' for clicado
                Button btnSalvar = new Button("Salvar", evento -> {
                    if (comentarios.getId() == null) {
                        comentarios.setDataComentario(LocalDateTime.now());
                    }
                    if (binder.writeBeanIfValid(comentarios)) {
                        consumer.accept(comentarios);
                        comentariosService.salvar(comentarios);
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
