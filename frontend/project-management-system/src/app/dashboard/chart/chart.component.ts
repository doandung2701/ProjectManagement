import { TaskService } from 'src/app/services/task.service';
import { Component, OnInit } from '@angular/core';
import { CountTaskByProjectViewModel } from 'src/app/model/response/CountTaskByProjectViewModel';

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit {
  data: any;
  options: any;
  lstData:CountTaskByProjectViewModel[];
  constructor(private taskService: TaskService) {
    this.options= {
      responsive: false,
      scales: {
        xAxes: [{
          barPercentage: 0.2
        }],
        yAxes: [{
          ticks: {
            beginAtZero: true,
            stepSize: 1
          }
        }]
      }
    };
    this.taskService.countTaskByProjectIdOfUser().subscribe(response=>{
      this.lstData=response;
      this.data={
        labels: response.map(x=>x.projectName),
        datasets: [{
          label: 'Number of task',
          data: response.map(x=>x.numOfTask),
          borderWidth: 1
        }]
      }
    });
  }

  ngOnInit() {
  }

}
