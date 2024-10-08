import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ConfigurationService from './configuration.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CoreConfiguration extends Vue {
  public orderProp = 'prefix';
  public reverse = false;
  public allConfiguration: any = false;
  public configuration: any = false;
  public configKeys: any[] = [];
  public filtered = '';
  @Inject('configurationService') private configurationService: () => ConfigurationService;

  public mounted(): void {
    this.init();
  }

  public init(): void {
    this.configurationService()
      .loadConfiguration()
      .then(res => {
        this.configuration = res;

        for (const config of this.configuration) {
          if (config.properties !== undefined) {
            this.configKeys.push(Object.keys(config.properties));
          }
        }
      });

    this.configurationService()
      .loadEnvConfiguration()
      .then(res => {
        this.allConfiguration = res;
      });
  }

  public changeOrder(prop: string): void {
    this.orderProp = prop;
    this.reverse = !this.reverse;
  }

  public keys(dict: any): string[] {
    return dict === undefined ? [] : Object.keys(dict);
  }
}
