<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="firma">
        <h2 class="jh-entity-heading" data-cy="firmaDetailsHeading">
          <span v-text="$t('sampleSentryApp.firma.detail.title')">Firma</span> {{ firma.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('sampleSentryApp.firma.rol')">Rol</span>
          </dt>
          <dd>
            <span>{{ firma.rol }}</span>
          </dd>
          <dt>
            <span v-text="$t('sampleSentryApp.firma.documentoUri')">Documento Uri</span>
          </dt>
          <dd>
            <span>{{ firma.documentoUri }}</span>
          </dd>
          <dt>
            <span v-text="$t('sampleSentryApp.firma.certificado')">Certificado</span>
          </dt>
          <dd>
            <span>{{ firma.certificado }}</span>
          </dd>
          <dt>
            <span v-text="$t('sampleSentryApp.firma.rfc')">Rfc</span>
          </dt>
          <dd>
            <span>{{ firma.rfc }}</span>
          </dd>
          <dt>
            <span v-text="$t('sampleSentryApp.firma.curp')">Curp</span>
          </dt>
          <dd>
            <span>{{ firma.curp }}</span>
          </dd>
          <dt>
            <span v-text="$t('sampleSentryApp.firma.signature')">Signature</span>
          </dt>
          <dd>
            <span>{{ firma.signature }}</span>
          </dd>
          <dt>
            <span v-text="$t('sampleSentryApp.firma.active')">Active</span>
          </dt>
          <dd>
            <span>{{ firma.active }}</span>
          </dd>
          <dt>
            <span v-text="$t('sampleSentryApp.firma.documento')">Documento</span>
          </dt>
          <dd>
            <div v-if="firma.documento">
              <router-link :to="{ name: 'DocumentoView', params: { documentoId: firma.documento.id } }">{{
                firma.documento.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="firma.id" :to="{ name: 'FirmaEdit', params: { firmaId: firma.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./firma-details.component.ts"></script>
