import { Component, OnInit } from '@angular/core';
import { HttpServiceService } from '../http-service.service';
import { Router } from '@angular/router';
import { DataValidator } from '../utility/data-validator';
import { CookieService } from 'ngx-cookie-service';
import { ActivatedRoute } from '@angular/router';
import { ServiceLocatorService } from '../service-locator.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  endpoint = "http://localhost:8084/Auth";

  form = {
    error: false,
    message: '',
    loginId: '',
    password: '',
    loginUrl: '',
  };

  inputerror = {};
  message = '';

  userparams = {
    url: '',
    sessionExpiredMsg: '',
    methodType: '',
  };

  constructor(
    private httpService: HttpServiceService,
    private dataValidator: DataValidator,
    private router: Router,
    private cookieService: CookieService,
    private route: ActivatedRoute,
    private serviceLocator: ServiceLocatorService
  ) {}

  ngOnInit() {
    if (this.httpService.form.error) {
      this.form.message = this.httpService.form.message;
      this.form.error = this.httpService.form.error;
    }

    this.serviceLocator.getPathVariable(this.route, (params) => {
      if (params["userparams"] === 'true') {
        this.form.message = 'Logout Successfully';
      }
    });
  }

  validate() {
    let flag = true;
    flag = flag && this.dataValidator.isNotNull(this.form.loginId);
    flag = flag && this.dataValidator.isNotNull(this.form.password);
    return flag;
  }

  signIn() {
    this.form.error = false;
    const requestedUrl = this.httpService.userparams.url;

    this.httpService.post(this.endpoint + "/login", this.form, (res) => {
      this.form.message = '';
      this.inputerror = {};

      if (this.dataValidator.isNotNullObject(res.result.message)) {
        this.form.message = res.result.message;
      }

      this.form.error = !res.success;

      if (this.dataValidator.isNotNullObject(res.result.inputerror)) {
        this.inputerror = res.result.inputerror;
      }

      if (this.dataValidator.isTrue(res.success)) {
        this.httpService.setToken(res.result.token);

        localStorage.setItem("loginId", res.result.loginId);
        localStorage.setItem("token", "Bearer " + res.result.token);
        localStorage.setItem("role", res.result.role);
        localStorage.setItem("fname", res.result.fname);
        localStorage.setItem("lname", res.result.lname);
        localStorage.setItem("userid", res.result.data.id);

        if (requestedUrl) {
          this.router.navigateByUrl(requestedUrl);
        } else {
          this.router.navigateByUrl('dashboard');
        }
      }
    });
  }
}
