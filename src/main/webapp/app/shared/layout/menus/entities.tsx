import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/corp">
      Corp
    </MenuItem>
    <MenuItem icon="asterisk" to="/crm-custom">
      Crm Custom
    </MenuItem>
    <MenuItem icon="asterisk" to="/team-grp">
      Team Grp
    </MenuItem>
    <MenuItem icon="asterisk" to="/manager">
      Manager
    </MenuItem>
    <MenuItem icon="asterisk" to="/tm-manager">
      Tm Manager
    </MenuItem>
    <MenuItem icon="asterisk" to="/memo-his">
      Memo His
    </MenuItem>
    <MenuItem icon="asterisk" to="/send-sms-his">
      Send Sms His
    </MenuItem>
    <MenuItem icon="asterisk" to="/stock-contract-his">
      Stock Contract His
    </MenuItem>
    <MenuItem icon="asterisk" to="/stock-consulting-his">
      Stock Consulting His
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
