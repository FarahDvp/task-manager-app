import { Component, OnInit } from '@angular/core';
// Import Chart.js in your Angular component
import { Chart } from 'chart.js/auto';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-dashboard-admin',
  templateUrl: './dashboard-admin.component.html',
  styleUrls: ['./dashboard-admin.component.css']
})
export class DashboardAdminComponent implements OnInit {
  // Properties to store API data
  doneTasks?: number;
  delayedTasks?: number;
  allTasks?: number;

  // Chart property
  pieChart: any;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    // Fetch data from the Spring Boot API
    this.http.get<any>('http://localhost:8085/api/tasks/statistics').subscribe(data => {
      this.doneTasks = data.done;
      this.delayedTasks = data.delayed;
      this.allTasks = data.all;

      // Call the method to create the pie chart
      this.createPieChart();
    });
  }

  createPieChart(): void {
    this.pieChart = new Chart('canvas', {
      type: 'pie',
      data: {
        labels: ['Done Tasks', 'Delayed Tasks', 'Remaining Tasks'],
        datasets: [{
          data: [this.doneTasks, this.delayedTasks, this.allTasks! - this.doneTasks! - this.delayedTasks!],
          backgroundColor: [
            'rgba(75, 192, 192, 0.5)', // Done Tasks color
            'rgba(255, 99, 132, 0.5)', // Delayed Tasks color
            'rgba(255, 255, 255, 0)'   // Remaining Tasks color (transparent)
          ],
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'bottom', // Display legend below the chart
          },
        },
      },
    });
  }
}
