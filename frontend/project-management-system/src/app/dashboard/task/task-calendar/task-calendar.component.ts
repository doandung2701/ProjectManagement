import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import { GlobalService } from 'src/app/services/global.service';
import { TaskService } from 'src/app/services/task.service';

@Component({
  selector: 'app-task-calendar',
  templateUrl: './task-calendar.component.html',
  styleUrls: ['./task-calendar.component.css']
})
export class TaskCalendarComponent implements OnInit {

  events: any[];
  options: any;

  constructor(private service: TaskService, private global: GlobalService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
     this.service.getAllTaskCalendar().subscribe(data => {
      this.events = data.events.map(data=>{
        return {
          id: data.taskId,
            title: data.title,
            start: data.start,
            end: data.end,
        }
      });
      console.log(this.events);
     });
    this.options = {
      plugins: [dayGridPlugin, timeGridPlugin, interactionPlugin],
      defaultDate: Date.now(),
      header: {
        left: 'prev,next',
        center: 'title',
        right: 'month,agendaWeek,agendaDay'
    },
      editable: true,
      eventClick: (e) => {
         this.handleDateClick(e);
         }
    };
  }
  handleDateClick($event){
    console.log($event);
    var taskId=$event.event.id;
    this.router.navigate([`/dashboard/task/detail/${taskId}`]);
  }
}
