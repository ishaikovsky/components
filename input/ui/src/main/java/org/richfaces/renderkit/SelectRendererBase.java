/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.richfaces.renderkit;

import java.io.IOException;
import java.util.List;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.richfaces.component.AbstractSelect;
import org.richfaces.component.AbstractSelectComponent;
import org.richfaces.renderkit.util.HtmlDimensions;

/**
 * @author abelevich
 *
 */
@ResourceDependencies({ @ResourceDependency(library = "javax.faces", name = "jsf.js"), @ResourceDependency(name = "jquery.js"),
        @ResourceDependency(name = "jquery.position.js"), @ResourceDependency(name = "richfaces.js"),
        @ResourceDependency(name = "richfaces-utils.js"), @ResourceDependency(name = "jquery.position.js"),
        @ResourceDependency(name = "richfaces-event.js"), @ResourceDependency(name = "richfaces-base-component.js"),
        @ResourceDependency(name = "richfaces-selection.js"),
        @ResourceDependency(library = "org.richfaces", name = "inputBase.js"),
        @ResourceDependency(library = "org.richfaces", name = "popup.js"),
        @ResourceDependency(library = "org.richfaces", name = "list.js"),
        @ResourceDependency(library = "org.richfaces", name = "popupList.js"),
        @ResourceDependency(library = "org.richfaces", name = "select.js"),
        @ResourceDependency(library = "org.richfaces", name = "select.ecss") })
public class SelectRendererBase extends InputRendererBase {
    public static final String ITEM_CSS = "rf-sel-opt";

    public void renderListHandlers(FacesContext facesContext, UIComponent component) throws IOException {
        RenderKitUtils.renderPassThroughAttributesOptimized(facesContext, component,
            SelectHelper.SELECT_LIST_HANDLER_ATTRIBUTES);
    }

    public List<ClientSelectItem> getConvertedSelectItems(FacesContext facesContext, UIComponent component) {
        return SelectHelper.getConvertedSelectItems(facesContext, component);
    }

    public String getSelectInputLabel(FacesContext facesContext, UIComponent component) {
        return SelectHelper.getSelectInputLabel(facesContext, component);
    }

    protected String getMinListHeight(AbstractSelect select) {
        String height = HtmlDimensions.formatSize(select.getMinListHeight());
        if (height == null || height.length() == 0) {
            height = "20px";
        }
        return height;
    }

    protected String getMaxListHeight(AbstractSelect select) {
        String height = HtmlDimensions.formatSize(select.getMaxListHeight());
        if (height == null || height.length() == 0) {
            height = "100px";
        }
        return height;
    }

    protected String getListHeight(AbstractSelect select) {
        String height = HtmlDimensions.formatSize(select.getListHeight());
        if (height == null || height.length() == 0) {
            height = "auto";
        }
        return height;
    }

    protected String getListWidth(AbstractSelect select) {
        String width = HtmlDimensions.formatSize(select.getListWidth());
        if (width == null || width.length() == 0) {
            width = "200px";
        }
        return width;
    }

    public String encodeHeightAndWidth(UIComponent component) {
        AbstractSelect select = (AbstractSelect) component;

        String height = getListHeight(select);
        if (!"auto".equals(height)) {
            height = (height != null && height.trim().length() != 0) ? ("height: " + height) : "";
        } else {
            String minHeight = getMinListHeight(select);
            minHeight = (minHeight != null && minHeight.trim().length() != 0) ? ("min-height: " + minHeight) : "";

            String maxHeight = getMaxListHeight(select);
            maxHeight = (maxHeight != null && maxHeight.trim().length() != 0) ? ("max-height: " + maxHeight) : "";
            height = concatStyles(minHeight, maxHeight);
        }

        String width = getListWidth(select);
        width = (width != null && width.trim().length() != 0) ? ("width: " + width) : "";

        return concatStyles(height, width);
    }

    public String getListCss(UIComponent component) {
        AbstractSelect inplaceSelect = (AbstractSelect) component;
        String css = inplaceSelect.getListClass();
        css = (css != null) ? concatClasses("rf-sel-lst-cord", css) : "rf-sel-lst-cord";
        return css;
    }

    public String getSelectLabel(FacesContext facesContext, UIComponent component) {
        AbstractSelectComponent select = (AbstractSelectComponent) component;
        String label = getSelectInputLabel(facesContext, select);
        if (label == null || "".equals(label.trim())) {
            label = select.getDefaultLabel();
        }
        return label;
    }

    public void encodeItems(FacesContext facesContext, UIComponent component, List<ClientSelectItem> clientSelectItems)
        throws IOException {
        SelectHelper.encodeItems(facesContext, component, clientSelectItems, HtmlConstants.DIV_ELEM, ITEM_CSS);
    }
}
