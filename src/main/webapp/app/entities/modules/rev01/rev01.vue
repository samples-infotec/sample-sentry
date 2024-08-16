<template>
  <div>
    <b-form-group
      id="fieldset-1"
      description="Nombre de la convocatoria."
      label="Ingrese el nombre de la convocatoria"
      label-for="input-1"
      valid-feedback="Gracias"
    >
      <b-form-input id="input-1" v-model="nombre" trim></b-form-input>
    </b-form-group>

    <b-button @click="enviar">Enviar</b-button>
  </div>
</template>

<script lang="ts" src="./rev01.component.ts"></script>
