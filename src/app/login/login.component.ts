import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent {
  username!: string;
  password!: string;
  errorMessage!: string;

  constructor(private authService: AuthService, private router: Router) {}

  login(): void {
    this.authService.login(this.username, this.password).subscribe(
      () => {
        this.router.navigate(['/todo']); // Redirect to todo page on successful login
      },
      error => {
        this.errorMessage = error.message; // Display error message
      }
    );
  }
}