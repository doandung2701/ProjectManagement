import { TaskService } from 'src/app/services/task.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit {
  data: any;
  options: any;
  constructor(private taskSerivce: TaskService) {
    this.taskSerivce.getAllTaskOfUser().subscribe(dataSet => {
      let labels = [...dataSet.events.map(x => x.start), ...dataSet.events.map(x => x.end)].sort(function (a: string, b: string) {
        return new Date(a).getTime() - new Date(b).getTime();
      });
      let datasets = [];
      for (let index = 0; index < dataSet.events.length; index++) {
        let element = dataSet.events[index];
        let startTimeIndex = labels.findIndex(x => x == element.start);
        let endTimeIndex = labels.findIndex(x => x == element.end);
        datasets.push({
           label: element.title,
          data: new Array(labels.length).fill(element.title, startTimeIndex, endTimeIndex + 1)
        });

      }
      debugger;
      this.data = {
        labels: labels,
        datasets:datasets
      }
      this.options = {
        barShowStroke: false,
        scaleShowGridLines: true,
        scaleOverride: true,
        scaleStartValue: 1,
        scaleStepWidth: 1,
        responsive: true,
        barBeginAtOrigin: true,
      }
    });
    //   this.data = {
    //     labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
    //     datasets: [
    //         {
    //             label: 'First Dataset',
    //             data: [65, 59, 80, 81, 56, 55, 40]
    //         },
    //         {
    //             label: 'Second Dataset',
    //             data: [28, 48, 40, 19, 86, 27, 90]
    //         }
    //     ]
    // }
  }

  ngOnInit() {
  }

}
