<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="documento">
        <h2 class="jh-entity-heading" data-cy="documentoDetailsHeading">
          <span v-text="$t('sampleSentryApp.documento.detail.title')">Documento</span> {{ documento.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('sampleSentryApp.documento.uri')">Uri</span>
          </dt>
          <dd>
            <span>{{ documento.uri }}</span>
          </dd>
          <dt>
            <span v-text="$t('sampleSentryApp.documento.data')">Data</span>
          </dt>
          <dd>
            <span>{{ documento.data }}</span>
          </dd>
          <dt>
            <span v-text="$t('sampleSentryApp.documento.proyecto')">Proyecto</span>
          </dt>
          <dd>
            <div v-if="documento.proyecto">
              <router-link :to="{ name: 'ProyectoView', params: { proyectoId: documento.proyecto.id } }">{{
                documento.proyecto.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="documento.id"
          :to="{ name: 'DocumentoEdit', params: { documentoId: documento.id } }"
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

<script lang="ts" src="./documento-details.component.ts"></script>
