import HealthService from './health.service';
import CoreHealthModal from './health-modal.vue';
import { Component, Inject, Vue } from 'vue-property-decorator';

@Component({
  components: {
    'health-modal': CoreHealthModal,
  },
})
export default class CoreHealth extends Vue {
  public healthData: any = null;
  public currentHealth: any = null;
  public updatingHealth = false;
  @Inject('healthService') private healthService: () => HealthService;

  public mounted(): void {
    this.refresh();
  }

  public baseName(name: any): any {
    return this.healthService().getBaseName(name);
  }

  public getBadgeClass(statusState: any): string {
    if (statusState === 'UP') {
      return 'badge-success';
    }
    return 'badge-danger';
  }

  public refresh(): void {
    this.updatingHealth = true;
    this.healthService()
      .checkHealth()
      .then(res => {
        this.healthData = this.healthService().transformHealthData(res.data);
        this.updatingHealth = false;
      })
      .catch(error => {
        if (error.status === 503) {
          this.healthData = this.healthService().transformHealthData(error.error);
        }
        this.updatingHealth = false;
      });
  }

  public showHealth(health: any): void {
    this.currentHealth = health;
    (<any>this.$refs.healthModal).show();
  }

  public subSystemName(name: string): string {
    return this.healthService().getSubSystemName(name);
  }
}
