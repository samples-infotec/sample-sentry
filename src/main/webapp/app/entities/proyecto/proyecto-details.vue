<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="proyecto">
        <h2 class="jh-entity-heading" data-cy="proyectoDetailsHeading">
          <span v-text="$t('sampleSentryApp.proyecto.detail.title')">Proyecto</span> {{ proyecto.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('sampleSentryApp.proyecto.detalle')">Detalle</span>
          </dt>
          <dd>
            <span>{{ proyecto.detalle }}</span>
          </dd>
          <dt>
            <span v-text="$t('sampleSentryApp.proyecto.modalidad')">Modalidad</span>
          </dt>
          <dd>
            <span>{{ proyecto.modalidad }}</span>
          </dd>
          <dt>
            <span v-text="$t('sampleSentryApp.proyecto.titulo')">Titulo</span>
          </dt>
          <dd>
            <span>{{ proyecto.titulo }}</span>
          </dd>
          <dt>
            <span v-text="$t('sampleSentryApp.proyecto.solicitudClave')">Solicitud Clave</span>
          </dt>
          <dd>
            <span>{{ proyecto.solicitudClave }}</span>
          </dd>
          <dt>
            <span v-text="$t('sampleSentryApp.proyecto.solicitudFecha')">Solicitud Fecha</span>
          </dt>
          <dd>
            <span v-if="proyecto.solicitudFecha">{{ $d(Date.parse(proyecto.solicitudFecha), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('sampleSentryApp.proyecto.completo')">Completo</span>
          </dt>
          <dd>
            <span>{{ proyecto.completo }}</span>
          </dd>
          <dt>
            <span v-text="$t('sampleSentryApp.proyecto.convocatoria')">Convocatoria</span>
          </dt>
          <dd>
            <div v-if="proyecto.convocatoria">
              <router-link :to="{ name: 'ConvocatoriaView', params: { convocatoriaId: proyecto.convocatoria.id } }">{{
                proyecto.convocatoria.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="proyecto.id" :to="{ name: 'ProyectoEdit', params: { proyectoId: proyecto.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./proyecto-details.component.ts"></script>
