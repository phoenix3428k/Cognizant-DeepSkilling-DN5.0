import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbstractControl } from '@angular/forms';
import { ReactiveFormsModule, FormBuilder, FormGroup, FormArray, FormControl, Validators, ValidationErrors } from '@angular/forms';
import { CourseService } from '../../services/course.service';

function noCourseCode(control: AbstractControl): ValidationErrors | null {
  if (control.value && String(control.value).startsWith('XX')) {
    return { noCourseCode: true };
  }
  return null;
}

function simulateEmailCheck(control: AbstractControl): Promise<ValidationErrors | null> {
  return new Promise(resolve => {
    setTimeout(() => {
      if (control.value && control.value.includes('test@')) {
        resolve({ emailTaken: true });
      }
      else {
        resolve(null);
      }
    }, 800);
  });
}

@Component({
  selector: 'app-reactive-enrollment-form',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './reactive-enrollment-form.html',
  styleUrl: './reactive-enrollment-form.css'
})
export class ReactiveEnrollmentForm implements OnInit {
  enrollForm!: FormGroup;
  submitted = false;

  constructor(private fb: FormBuilder, private courseService: CourseService) {}

  ngOnInit() {
    this.enrollForm = this.fb.group({
      studentName: ['', [Validators.required, Validators.minLength(3)]],
      studentEmail: this.fb.control('', [Validators.required, Validators.email], [simulateEmailCheck]),
      courseId: [null, [Validators.required, noCourseCode]],
      preferredSemester: ['Odd', Validators.required],
      agreeToTerms: [false, Validators.requiredTrue],
      additionalCourses: this.fb.array([])
    });
  }

  // this getter is better than casting in the template because it keeps the template clean and provides type safety
  get additionalCourses(): FormArray {
    return this.enrollForm.get('additionalCourses') as FormArray;
  }

  asFormControl(ctrl: AbstractControl): FormControl {
    return ctrl as FormControl;
  }

  addCourse() {
    this.additionalCourses.push(this.fb.control('', Validators.required));
  }

  removeCourse(index: number) {
    this.additionalCourses.removeAt(index);
  }

  onSubmit() {
    console.log(this.enrollForm.value);
    // enrollForm.value excludes disabled controls, while getRawValue() includes all controls
    console.log(this.enrollForm.getRawValue());
    this.submitted = true;
    this.courseService.createCourse(this.enrollForm.value).subscribe({
      next: course => console.log('Course Created:', course),
      error: err => console.error('Error Creating Course:', err)
    });
  }
}
