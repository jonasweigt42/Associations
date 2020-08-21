// eagerly import theme styles so as we can override them
import '@vaadin/vaadin-lumo-styles/all-imports';

import '@vaadin/vaadin-charts/theme/vaadin-chart-default-theme';

const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `
/* <custom-style>
  <style>
    html {
    	 --lumo-primary-text-color: var(--primary-color, #147843);
         --lumo-primary-color: var(--primary-color, #147843);
    }
  </style>
</custom-style>
*/

<custom-style>
  <style>
    html {
      overflow:hidden;
    }
    vaadin-app-layout vaadin-tab a:hover {
      text-decoration: none;
    }
  </style>
</custom-style>

<dom-module id="app-layout-theme" theme-for="vaadin-app-layout">
  <template>
    <style>
      [part="drawer"] {
		max-width: 150px;
      }
      
     </style>
  </template>
</dom-module>

`;

document.head.appendChild($_documentContainer.content);

