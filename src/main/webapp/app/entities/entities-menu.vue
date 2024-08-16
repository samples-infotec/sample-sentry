<template>
  <div>
    <b-dropdown-item to="/convocatoria">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.convocatoria')">Convocatoria</span>
    </b-dropdown-item>
    <b-dropdown-item to="/proyecto">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.proyecto')">Proyecto</span>
    </b-dropdown-item>
    <b-dropdown-item to="/documento">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.documento')">Documento</span>
    </b-dropdown-item>
    <b-dropdown-item to="/firma">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.firma')">Firma</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
