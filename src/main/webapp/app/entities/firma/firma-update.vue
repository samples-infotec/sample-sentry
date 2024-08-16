<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="sampleSentryApp.firma.home.createOrEditLabel"
          data-cy="FirmaCreateUpdateHeading"
          v-text="$t('sampleSentryApp.firma.home.createOrEditLabel')"
        >
          Create or edit a Firma
        </h2>
        <div>
          <div class="form-group" v-if="firma.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="firma.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.firma.rol')" for="firma-rol">Rol</label>
            <input
              type="text"
              class="form-control"
              name="rol"
              id="firma-rol"
              data-cy="rol"
              :class="{ valid: !$v.firma.rol.$invalid, invalid: $v.firma.rol.$invalid }"
              v-model="$v.firma.rol.$model"
              required
            />
            <div v-if="$v.firma.rol.$anyDirty && $v.firma.rol.$invalid">
              <small class="form-text text-danger" v-if="!$v.firma.rol.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.firma.documentoUri')" for="firma-documentoUri"
              >Documento Uri</label
            >
            <input
              type="text"
              class="form-control"
              name="documentoUri"
              id="firma-documentoUri"
              data-cy="documentoUri"
              :class="{ valid: !$v.firma.documentoUri.$invalid, invalid: $v.firma.documentoUri.$invalid }"
              v-model="$v.firma.documentoUri.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.firma.certificado')" for="firma-certificado">Certificado</label>
            <input
              type="text"
              class="form-control"
              name="certificado"
              id="firma-certificado"
              data-cy="certificado"
              :class="{ valid: !$v.firma.certificado.$invalid, invalid: $v.firma.certificado.$invalid }"
              v-model="$v.firma.certificado.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.firma.rfc')" for="firma-rfc">Rfc</label>
            <input
              type="text"
              class="form-control"
              name="rfc"
              id="firma-rfc"
              data-cy="rfc"
              :class="{ valid: !$v.firma.rfc.$invalid, invalid: $v.firma.rfc.$invalid }"
              v-model="$v.firma.rfc.$model"
              required
            />
            <div v-if="$v.firma.rfc.$anyDirty && $v.firma.rfc.$invalid">
              <small class="form-text text-danger" v-if="!$v.firma.rfc.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.firma.curp')" for="firma-curp">Curp</label>
            <input
              type="text"
              class="form-control"
              name="curp"
              id="firma-curp"
              data-cy="curp"
              :class="{ valid: !$v.firma.curp.$invalid, invalid: $v.firma.curp.$invalid }"
              v-model="$v.firma.curp.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.firma.signature')" for="firma-signature">Signature</label>
            <input
              type="text"
              class="form-control"
              name="signature"
              id="firma-signature"
              data-cy="signature"
              :class="{ valid: !$v.firma.signature.$invalid, invalid: $v.firma.signature.$invalid }"
              v-model="$v.firma.signature.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.firma.active')" for="firma-active">Active</label>
            <input
              type="checkbox"
              class="form-check"
              name="active"
              id="firma-active"
              data-cy="active"
              :class="{ valid: !$v.firma.active.$invalid, invalid: $v.firma.active.$invalid }"
              v-model="$v.firma.active.$model"
              required
            />
            <div v-if="$v.firma.active.$anyDirty && $v.firma.active.$invalid">
              <small class="form-text text-danger" v-if="!$v.firma.active.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('sampleSentryApp.firma.documento')" for="firma-documento">Documento</label>
            <select class="form-control" id="firma-documento" data-cy="documento" name="documento" v-model="firma.documento">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="firma.documento && documentoOption.id === firma.documento.id ? firma.documento : documentoOption"
                v-for="documentoOption in documentos"
                :key="documentoOption.id"
              >
                {{ documentoOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.firma.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./firma-update.component.ts"></script>
