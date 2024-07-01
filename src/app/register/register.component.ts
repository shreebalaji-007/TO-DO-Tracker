import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {  
  id!: number;
  username!: string;
  password!: string;
  email!: string;
  errorMessage!: string;

  constructor(private userService: UserService, private router: Router, private _snackBar: MatSnackBar) {}

  register(): void {
    const newUser = {      
      id: this.id,
      username: this.username,
      password: this.password,
      email: this.email
    };

    this.userService.createUser(newUser).subscribe(
      () => {
        // Redirect to login page after successful registration
        this.openSnackBar("User registered successfully, you will be redirected to login page shortly.");
        this.router.navigate(['/login']);
      },
      error => {
        this.errorMessage = error.message; // Display error message
      }
    );
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "OK", {
      duration: 5000
    });
  }
}