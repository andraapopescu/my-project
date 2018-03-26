/*
 * Copyright 2000-2014 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package application.demo.ui.layouts;

import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
/**
 * 
 * @since
 * @author Vaadin Ltd
 */
public class ValoMenuLayout extends HorizontalLayout {
	private static final long serialVersionUID = 1L;
	
	CssLayout contentArea = new CssLayout();
    CssLayout rightMenuArea = new CssLayout();
    CssLayout leftMenuArea = new CssLayout();

    public ValoMenuLayout() {
        setSizeFull();

        rightMenuArea.setPrimaryStyleName("valo-menu");
        leftMenuArea.setPrimaryStyleName("valo-menu");

        contentArea.setPrimaryStyleName("valo-content");
        contentArea.addStyleName("v-scrollable");
        contentArea.setSizeFull();

        contentArea.setSizeFull();
        
        addComponents(leftMenuArea, contentArea, rightMenuArea);
        setExpandRatio(contentArea, 1);
    }

    public ComponentContainer getContentContainer() {
        return contentArea;
    }

    public void addRightMenu(Component menu) {
        menu.addStyleName("valo-menu-part");
        rightMenuArea.addComponent(menu);
    }
    public void addLeftMenu(Component menu) {
        menu.addStyleName("valo-menu-part");
        leftMenuArea.addComponent(menu);
    }

}
