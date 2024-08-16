<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="convocatoria">
        <h2 class="jh-entity-heading" data-cy="convocatoriaDetailsHeading">
          <span v-text="$t('sampleSentryApp.convocatoria.detail.title')">Convocatoria</span> {{ convocatoria.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('sampleSentryApp.convocatoria.nombre')">Nombre</span>
          </dt>
          <dd>
            <span>{{ convocatoria.nombre }}</span>
          </dd>
          <dt>
            <span v-text="$t('sampleSentryApp.convocatoria.anio')">Anio</span>
          </dt>
          <dd>
            <span>{{ convocatoria.anio }}</span>
          </dd>
          <dt>
            <span v-text="$t('sampleSentryApp.convocatoria.fechaCreacion')">Fecha Creacion</span>
          </dt>
          <dd>
            <span>{{ convocatoria.fechaCreacion }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="convocatoria.id"
          :to="{ name: 'ConvocatoriaEdit', params: { convocatoriaId: convocatoria.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./convocatoria-details.component.ts"></script>
