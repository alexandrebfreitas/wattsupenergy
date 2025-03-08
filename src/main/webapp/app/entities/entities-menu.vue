<template>
  <div>
    <b-dropdown-item to="/acomph">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.acomph')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/posto-medicao">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.postoMedicao')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
