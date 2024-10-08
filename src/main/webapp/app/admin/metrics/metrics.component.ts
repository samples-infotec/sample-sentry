import numeral from 'numeral';
import CoreMetricsModal from './metrics-modal.vue';
import MetricsService from './metrics.service';
import { Component, Vue, Inject } from 'vue-property-decorator';

@Component({
  components: {
    'metrics-modal': CoreMetricsModal,
  },
})
export default class CoreMetrics extends Vue {
  public metrics: any = {};
  public threadData: any = null;
  public threadStats: any = {};
  public updatingMetrics = true;
  @Inject('metricsService')
  private metricsService: () => MetricsService;

  public mounted(): void {
    this.refresh();
  }

  public refresh(): void {
    this.metricsService()
      .getMetrics()
      .then(resultsMetrics => {
        this.metrics = resultsMetrics.data;
        this.metricsService()
          .retrieveThreadDump()
          .then(res => {
            this.updatingMetrics = true;
            this.threadData = res.data.threads;

            this.threadStats = {
              threadDumpRunnable: 0,
              threadDumpWaiting: 0,
              threadDumpTimedWaiting: 0,
              threadDumpBlocked: 0,
              threadDumpAll: 0,
            };

            this.threadData.forEach(value => {
              if (value.threadState === 'RUNNABLE') {
                this.threadStats.threadDumpRunnable += 1;
              } else if (value.threadState === 'WAITING') {
                this.threadStats.threadDumpWaiting += 1;
              } else if (value.threadState === 'TIMED_WAITING') {
                this.threadStats.threadDumpTimedWaiting += 1;
              } else if (value.threadState === 'BLOCKED') {
                this.threadStats.threadDumpBlocked += 1;
              }
            });

            this.threadStats.threadDumpAll =
              this.threadStats.threadDumpRunnable +
              this.threadStats.threadDumpWaiting +
              this.threadStats.threadDumpTimedWaiting +
              this.threadStats.threadDumpBlocked;

            this.updatingMetrics = false;
          })
          .catch(() => {
            this.updatingMetrics = true;
          });
      })
      .catch(() => {
        this.updatingMetrics = true;
      });
  }

  openModal(): void {
    if ((<any>this.$refs.metricsModal).show) {
      (<any>this.$refs.metricsModal).show();
    }
  }

  filterNaN(input: any): any {
    if (isNaN(input)) {
      return 0;
    }
    return input;
  }

  formatNumber1(value: any): any {
    return numeral(value).format('0,0');
  }

  formatNumber2(value: any): any {
    return numeral(value).format('0,00');
  }

  convertMillisecondsToDuration(ms) {
    const times = {
      year: 31557600000,
      month: 2629746000,
      day: 86400000,
      hour: 3600000,
      minute: 60000,
      second: 1000,
    };
    let time_string = '';
    let plural = '';
    for (const key in times) {
      if (Math.floor(ms / times[key]) > 0) {
        if (Math.floor(ms / times[key]) > 1) {
          plural = 's';
        } else {
          plural = '';
        }
        time_string += `${Math.floor(ms / times[key])} ${key}${plural} `;
        ms = ms - times[key] * Math.floor(ms / times[key]);
      }
    }
    return time_string;
  }

  public isObjectExisting(metrics: any, key: string): boolean {
    return metrics && metrics[key];
  }

  public isObjectExistingAndNotEmpty(metrics: any, key: string): boolean {
    return this.isObjectExisting(metrics, key) && JSON.stringify(metrics[key]) !== '{}';
  }
}
