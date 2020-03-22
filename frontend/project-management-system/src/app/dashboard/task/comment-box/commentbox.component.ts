import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';


@Component({
  selector: 'app-commentbox',
  templateUrl: './commentbox.component.html',
})
export class CommentboxComponent implements OnInit {


  commentForm: FormGroup;
  commentInfo: object;
  submitted: Boolean = false;
  public id = 0;
  @Output() usercomment = new EventEmitter();


  constructor(private formBuilder: FormBuilder) { }


  ngOnInit() {
    this.createForm();
  }


  createForm() {
    this.commentForm = this.formBuilder.group({
      comment: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(100)]]
    });
  }


  onSubmit() {
    this.submitted = true;
    // stop here if form is invalid
    if (this.commentForm.invalid) {
      return false;
    } else {
        this.commentInfo=new Object();
        this.commentInfo['content']=this.commentForm.controls['comment'].value;
      this.usercomment.emit(this.commentInfo);
    }
  }



}