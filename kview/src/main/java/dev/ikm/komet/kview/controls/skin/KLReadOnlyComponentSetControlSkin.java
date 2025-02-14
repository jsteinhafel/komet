package dev.ikm.komet.kview.controls.skin;

import dev.ikm.komet.kview.controls.ComponentItem;
import dev.ikm.komet.kview.controls.KLReadOnlyComponentSetControl;
import dev.ikm.komet.kview.controls.KometIcon;
import javafx.collections.SetChangeListener;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.SeparatorMenuItem;

public class KLReadOnlyComponentSetControlSkin extends KLReadOnlyMultiComponentControlSkin<KLReadOnlyComponentSetControl> {

    /**
     * @param control The control for which this Skin should attach to.
     */
    public KLReadOnlyComponentSetControlSkin(KLReadOnlyComponentSetControl control) {
        super(control);

        // sync items observableList
        for (ComponentItem componentItem : control.getItems()) {
            addNewUIItem(componentItem);
        }
        control.getItems().addListener(this::itemsChanged);
    }

    private void itemsChanged(SetChangeListener.Change<? extends ComponentItem> change) {
        if (change.wasAdded()) {
            addNewUIItem(change.getElementAdded());
        } else if (change.wasRemoved()) {
            removeUIItem(change.getElementRemoved());
        }
    }

    private void addNewUIItem(ComponentItem componentItem) {
        ComponentItemNode componentItemNode = new ComponentItemNode(componentItem);

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(
                createMenuItem("Edit Set", KometIcon.IconValue.PENCIL, this::fireOnEditAction),
                new SeparatorMenuItem(),
                createMenuItem("Remove", KometIcon.IconValue.TRASH, actionEvent -> this.fireOnRemoveAction(actionEvent, componentItem))
        );
        componentItemNode.setContextMenu(contextMenu);

        componentsContainer.getChildren().add(componentItemNode);
        componentUIItems.put(componentItem, componentItemNode);
    }
}