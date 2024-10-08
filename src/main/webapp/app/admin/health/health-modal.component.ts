import HealthService from './health.service';
import { Component, Inject, Prop, Vue } from 'vue-property-decorator';

@Component
export default class CoreHealthModal extends Vue {
  @Prop() currentHealth!: any;
  @Inject('healthService') private healthService: () => HealthService;

  public baseName(name: string): any {
    return this.healthService().getBaseName(name);
  }

  public subSystemName(name: string): any {
    return this.healthService().getSubSystemName(name);
  }

  public readableValue(value: any): string {
    if (this.currentHealth.name === 'diskSpace') {
      // Should display storage space in an human readable unit
      const val = value / 1073741824;
      if (val > 1) {
        // Value
        return val.toFixed(2) + ' GB';
      }
      return (value / 1048576).toFixed(2) + ' MB';
    }

    if (typeof value === 'object') {
      return JSON.stringify(value);
    }
    return value.toString();
  }
}
