import Vue from 'vue';
import { Component, Inject } from 'vue-property-decorator';
import UserManagementService from './user-management.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CoreUserManagementView extends Vue {
  @Inject('userManagementService') private userManagementService: () => UserManagementService;
  @Inject('alertService') private alertService: () => AlertService;

  public user: any = null;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userId) {
        vm.init(to.params.userId);
      }
    });
  }
  public init(userId: string): void {
    this.userManagementService()
      .get(userId)
      .then(res => {
        this.user = res.data;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }
}
