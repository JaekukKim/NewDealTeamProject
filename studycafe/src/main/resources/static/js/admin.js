const MONTHS = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

const CHART_COLORS = {
  red: "rgb(255, 99, 132)",
  orange: "rgb(255, 159, 64)",
  yellow: "rgb(255, 205, 86)",
  green: "rgb(75, 192, 192)",
  blue: "rgb(54, 162, 235)",
  purple: "rgb(153, 102, 255)",
  grey: "rgb(201, 203, 207)",
};

const DEFAULT_CHART_OPTIONS = {
  responsive: true,
};

const DEFAULT_TABLE_OPTIONS = {
  minHeight: 300,
  maxWidth: 400,
  layout: "fitColumns",
  responsiveLayout: "hide",
  history: true,
  pagination: "local",
  paginationSize: 10,
  paginationCounter: "rows",
  movableColumns: true,
  columnDefaults: {
    tooltip: true,
  },
};
// 유틸 함수

// Text를 Html DOM으로 변환
function htmlToElements(html) {
  var template = document.createElement("template");
  template.innerHTML = html;
  return template.content.childNodes;
}

// 테이블 필터 DOM
var fieldEl = document.getElementById("filter-field");
var typeEl = document.getElementById("filter-type");
var valueEl = document.getElementById("filter-value");

// 필터 업데이트 함수
function updateFilter(table) {
  var filterVal = fieldEl.options[fieldEl.selectedIndex].value;
  var typeVal = typeEl.options[typeEl.selectedIndex].value;

  typeEl.disabled = false;
  valueEl.disabled = false;

  if (filterVal) {
    table.setFilter(filterVal, typeVal, valueEl.value);
  }
}

// 필터 필드 설정 함수

function setFilterFields(cols) {
  cols.forEach((col) => {
    let option = document.createElement("option");
    option.value = col.field;
    option.innerHTML = col.title;
    fieldEl.appendChild(option);
  });
}

function createChart(el, type, data, options) {
  return new Chart(el, {
    type,
    data,
    options,
  });
}

function createHTTPRequest(url) {
  let httpRequest = new XMLHttpRequest();
  let result;
  if (!httpRequest) {
    console.error("ajax 요청을 만드는데 실패하였습니다.");
    return false;
  }

  httpRequest.onreadystatechange = function () {
    try {
      if (httpRequest.readyState === XMLHttpRequest.DONE) {
        if (this.readyState == 4 && httpRequest.status === 200) {
          result = httpRequest.responseText;
        } else {
          console.error("httpRequest 요청에 문제가 있습니다.");
        }
      }
    } catch (e) {
      alert(`Caught Exception: ${e.description}`);
    }
  };

  httpRequest.open("GET", url, false);
  httpRequest.send();
  return result;
}

function teamApprove(url, teamNum) {
  let httpRequest = new XMLHttpRequest();

  // 스피너 DOM 생성
  var spinner = document.createElement("div");
  spinner.insertAdjacentHTML(
    "beforeend",
    `<div class="spinner-border spin" role="status">
         <span class="sr-only">Loading...</span>
       </div>`
  );

  var team_table = document.getElementById("team-table-1");

  httpRequest.onreadystatechange = function () {
    try {
      if (httpRequest.readyState === XMLHttpRequest.DONE) {
        team_table.removeChild(spinner);
        team_table.classList.remove("loading");
        if (this.readyState == 4 && httpRequest.status === 200) {
          if (url.includes("dis")) alert("팀 승인을 해제하였습니다.");
          else alert("팀을 승인하였습니다.");
        } else {
          if (url.includes("dis")) console.error("팀 승인에 실패하였습니다.");
          else console.error("팀 승인 해제에 실패하였습니다.");
        }
      }
    } catch (e) {
      alert(`에러 발생: ${e.description}`);
    }
  };

  team_table.appendChild(spinner);
  team_table.classList.add("loading");

  httpRequest.open("POST", url);
  httpRequest.setRequestHeader("Content-Type", "application/json");
  httpRequest.send(JSON.stringify({ teamNum: teamNum }));
}

function renderDashboard() {
  // 대쉬보드 차트 설정
  const chrt1 = document.getElementById("home-donught-1").getContext("2d");

  const dashboard_data = createHTTPRequest("/admin/api/dashboard");
  console.log(JSON.parse(dashboard_data));

  const chrt1_data = {
    labels: ["팀1", "팀2", "팀3", "팀4", "팀5", "팀6"],
    datasets: [
      {
        label: "팀 점수 순위",
        data: [20, 40, 13, 35, 20, 38],
        backgroundColor: [
          "yellow",
          "aqua",
          "pink",
          "lightgreen",
          "gold",
          "lightblue",
        ],
        hoverOffset: 5,
      },
    ],
  };

  const chrt2 = document.getElementById("home-donught-2").getContext("2d");
  const chrt2_data = {
    labels: ["팀1", "팀2", "팀3", "팀4", "팀5", "팀6"],
    datasets: [
      {
        label: "인증 횟수 순위",
        data: [10, 20, 40, 23, 70, 44],
        backgroundColor: [
          "yellow",
          "aqua",
          "pink",
          "lightgreen",
          "gold",
          "lightblue",
        ],
        hoverOffset: 5,
      },
    ],
  };

  const chrt3 = document.getElementById("home-donught-3").getContext("2d");
  const chrt3_data = {
    labels: ["새로운 트래픽", "기존 트래픽"],
    datasets: [
      {
        label: "트래픽",
        data: [50, 100],
        backgroundColor: ["pink", "#F8F8FF"],
        hoverOffset: 5,
      },
    ],
  };

  const chrt4 = document.getElementById("home-donught-4").getContext("2d");
  const chrt4_data = {
    labels: ["새로운 유저", "기존 유저"],
    datasets: [
      {
        label: "유저",
        data: [22, 200],
        backgroundColor: ["lightgreen", "#F8F8FF"],
        hoverOffset: 5,
      },
    ],
  };

  createChart(chrt1, "doughnut", chrt1_data, DEFAULT_CHART_OPTIONS);
  createChart(chrt2, "doughnut", chrt2_data, DEFAULT_CHART_OPTIONS);
  createChart(chrt3, "pie", chrt3_data, DEFAULT_CHART_OPTIONS);
  createChart(chrt4, "pie", chrt4_data, DEFAULT_CHART_OPTIONS);

  const line1 = document.getElementById("home-line-1").getContext("2d");
  const line2 = document.getElementById("home-line-2").getContext("2d");

  const line_options = (text) => {
    return {
      responsive: true,
      interaction: {
        mode: "index",
        intersect: false,
      },
      plugins: {
        title: {
          display: true,
          text: text,
        },
      },
      scales: {
        y: {
          type: "linear",
          display: true,
          position: "left",
        },
        y1: {
          type: "linear",
          display: true,
          position: "right",

          grid: {
            drawOnChartArea: false,
          },
        },
      },
    };
  };

  const line_labels = MONTHS.slice(2, 10);
  const line1_data = {
    labels: line_labels,
    datasets: [
      {
        label: "등록 신청",
        data: [105, 203, 55, 68, 77, 105, 155],
        fill: false,
        borderColor: CHART_COLORS.green,
        // backgroundColor: rgba(75, 192, 192, 0.5),
        yAxisID: "y",
      },
      {
        label: "등록 완료",
        data: [42, 167, 50, 53, 55, 88, 102],
        fill: false,
        borderColor: CHART_COLORS.purple,
        // backgroundColor: rgba(153, 102, 255, 0.5),
        yAxisID: "y",
      },
    ],
  };

  const line2_data = {
    labels: line_labels,
    datasets: [
      {
        label: "예약 신청",
        data: [23, 54, 76, 25, 88, 42, 75],
        fill: false,
        borderColor: CHART_COLORS.red,
        // backgroundColor: rgba(75, 192, 192, 0.5),
        yAxisID: "y",
      },
      {
        label: "예약 완료",
        data: [20, 50, 60, 22, 66, 40, 59],
        fill: false,
        borderColor: CHART_COLORS.yellow,
        // backgroundColor: rgba(153, 102, 255, 0.5),
        yAxisID: "y",
      },
    ],
  };

  createChart(line1, "line", line1_data, line_options("팀 등록 현황"));
  createChart(line2, "line", line2_data, line_options("예약 현황"));
}

function renderTeam() {
  // 팀 차트 설정
  const team_data = JSON.parse(createHTTPRequest("/admin/api/team"));
  console.log(team_data);

  const chrt1 = document.getElementById("team-donught-1").getContext("2d");
  const chrt1_data = {
    labels: ["팀1", "팀2", "팀3", "팀4", "팀5", "팀6"],
    datasets: [
      {
        label: "팀 점수 랭킹",
        data: [20, 40, 13, 32, 45, 22],
        backgroundColor: [
          "yellow",
          "aqua",
          "pink",
          "lightgreen",
          "gold",
          "lightblue",
        ],
        hoverOffset: 5,
      },
    ],
  };
  const chrt2 = document.getElementById("team-donught-2").getContext("2d");
  const chrt2_data = {
    labels: ["팀1", "팀2", "팀3", "팀4", "팀5", "팀6"],
    datasets: [
      {
        label: "인증 횟수 랭킹",
        data: [25, 44, 15, 23, 70, 32],
        backgroundColor: [
          "yellow",
          "aqua",
          "pink",
          "lightgreen",
          "gold",
          "lightblue",
        ],
        hoverOffset: 5,
      },
    ],
  };

  const chrt3 = document.getElementById("team-donught-3").getContext("2d");
  const chrt3_data = {
    labels: ["새로운 팀", "기존 팀"],
    datasets: [
      {
        label: "팀",
        data: [23, 47],
        backgroundColor: ["DarkMagenta", "#F8F8FF"],
        hoverOffset: 5,
      },
    ],
  };

  createChart(chrt1, "doughnut", chrt1_data, DEFAULT_CHART_OPTIONS);
  createChart(chrt2, "doughnut", chrt2_data, DEFAULT_CHART_OPTIONS);
  createChart(chrt3, "pie", chrt3_data, DEFAULT_CHART_OPTIONS);

  const t1 = document.getElementById("team-table-1");
  const t2 = document.getElementById("team-table-2");

  // 테이블 컬럼 필드
  const table1_cols = [
    { title: "id", field: "teamNumber" },
    { title: "팀이름", field: "teamName" },
    { title: "헤드", field: "teamHead" },
    { title: "포인트", field: "point" },
    { title: "인증횟수", field: "approveCount" },
    { title: "팀 인원", field: "memberCount" },
    { title: "신청날짜", field: "createDate" },
    { title: "승인날짜", field: "approveDate" },
    {
      title: "승인",
      field: "approve",
      formatter: function (cell, formatterParams) {
        var teamNum = cell.getRow().getData().teamNumber;
        var switchEl = document.createElement("div");
        var approved = cell.getRow().getData().approveDate ? true : false;
        switchEl.insertAdjacentHTML(
          "beforeend",
          `<div class="form-check form-switch">
          <input class="form-check-input" type="checkbox" value="${
            approved ? "on" : "off"
          }" role="switch" id="flexSwitchCheckDefault" ${
            approved ? "checked" : ""
          }>
        </div>`
        );
        switchEl.addEventListener("click", (e) => {
          if (e.target.value === "off") {
            e.target.value = "on";

            teamApprove("/admin/api/team/approve", teamNum);
            cell.getRow().update;
          } else {
            e.target.value = "off";
            teamApprove("/admin/api/team/disapprove", teamNum);
          }
        });
        return switchEl;
      },
    },
  ];

  // 필터 필드 설정
  setFilterFields(table1_cols);

  const table1 = new Tabulator("#team-table-1", {
    index: "id",
    data: team_data.allTeams,
    layout: "fitcolumns",
    columns: table1_cols,
    ...DEFAULT_TABLE_OPTIONS,
  });

  // const table2 = new Tabulator("#team-table-2", {
  //   index: "id",
  //   layout: "fitcolumns",
  //   data: team_data.notApproveTeam,
  //   columns: [
  //     { title: "id", field: "teamNumber" },
  //     { title: "이름", field: "teamName" },
  //     { title: "헤드", field: "teamHead" },
  //     { title: "팀인원", field: "memberCount" },
  //     { title: "신청날짜", field: "createDate" },
  //   ],
  //   ...DEFAULT_TABLE_OPTIONS,
  // });

  return table1;
}

function renderUser() {
  // 유저 차트 설정

  const user_data = JSON.parse(createHTTPRequest("/admin/api/user"));
  // console.log(user_data);

  const chrt1 = document.getElementById("user-donught-1").getContext("2d");
  const chrt1_data = {
    labels: ["유저1", "유저2", "유저3", "유저4", "유저5", "유저6"],
    datasets: [
      {
        label: "유저 점수 랭킹",
        data: [354, 200, 138, 205, 304, 322],
        backgroundColor: [
          "yellow",
          "aqua",
          "pink",
          "lightgreen",
          "gold",
          "lightblue",
        ],
        hoverOffset: 5,
      },
    ],
  };

  const chrt2 = document.getElementById("user-donught-2").getContext("2d");
  const chrt2_data = {
    labels: ["유저1", "유저2", "유저3", "유저4", "유저5", "유저6"],
    datasets: [
      {
        label: "유저 활동 랭킹",
        data: [11, 22, 16, 54, 20, 32],
        backgroundColor: [
          "DarkViolet",
          "aqua",
          "DeepPink",
          "lightgreen",
          "Plum",
          "LavenderBlush",
        ],
        hoverOffset: 5,
      },
    ],
  };

  const chrt3 = document.getElementById("user-donught-3").getContext("2d");
  const chrt3_data = {
    labels: ["새로운 유저", "기존 유저"],
    datasets: [
      {
        label: "유저",
        data: [53, 412],
        backgroundColor: ["DarkCyan", "#F8F8FF"],
        hoverOffset: 5,
      },
    ],
  };

  createChart(chrt1, "doughnut", chrt1_data, DEFAULT_CHART_OPTIONS);
  createChart(chrt2, "doughnut", chrt2_data, DEFAULT_CHART_OPTIONS);
  createChart(chrt3, "pie", chrt3_data, DEFAULT_CHART_OPTIONS);

  const table1_cols = [
    { title: "아이디", field: "username" },
    { title: "이름", field: "name" },
    { title: "닉네임", field: "nickname" },
    { title: "이메일", field: "email" },
    { title: "역할", field: "role" },
    { title: "팀번호", field: "teamNumber" },
    { title: "생성날짜", field: "createdAt" },
  ];

  setFilterFields(table1_cols);

  const table1 = new Tabulator("#user-table-1", {
    index: "id",
    data: user_data.allMember,
    columns: table1_cols,
    ...DEFAULT_TABLE_OPTIONS,
  });

  return table1;
}

function renderStudy() {
  // 스터디 차트 설정

  const study_data = JSON.parse(createHTTPRequest("/admin/api/study"));

  const chrt1 = document.getElementById("study-pie-1").getContext("2d");
  const chrt1_data = {
    labels: ["스터디 신청", "스터디 진행", "스터디 종료"],
    datasets: [
      {
        label: "스터디",
        data: [53, 234, 164],
        backgroundColor: ["lightpink", "lightblue", "yellow"],
        hoverOffset: 5,
      },
    ],
  };

  const chrt2 = document.getElementById("study-pie-2").getContext("2d");
  const chrt2_data = {
    labels: ["스터디1", "스터디2", "스터디3", "스터디4", "스터디5", "스터디6"],
    datasets: [
      {
        label: "스터디 점수 랭킹",
        data: [11, 22, 16, 54, 20, 32],
        backgroundColor: [
          "DarkViolet",
          "aqua",
          "DeepPink",
          "lightgreen",
          "Plum",
          "LavenderBlush",
        ],
        hoverOffset: 5,
      },
    ],
  };

  createChart(chrt1, "doughnut", chrt1_data, DEFAULT_CHART_OPTIONS);
  createChart(chrt2, "doughnut", chrt2_data, DEFAULT_CHART_OPTIONS);

  const table1_cols = [
    { title: "ID", field: "studyNum" },
    { title: "작성자", field: "studyWriter" },
    { title: "내용", field: "studyContent" },
    { title: "제목", field: "studyTitle" },
    { title: "위도", field: "latitude" },
    { title: "경도", field: "longitude" },
    { title: "예약날짜", field: "reserveDate" },
    { title: "예약시간", field: "reserveTime" },
    { title: "등록일자", field: "dateTime" },
  ];

  const table1 = new Tabulator("#study-table-1", {
    index: "id",
    data: study_data.allStudies,
    columns: table1_cols,
    ...DEFAULT_TABLE_OPTIONS,
  });

  setFilterFields(table1_cols);

  return table1;
}


// 메인 실행 함수
document.addEventListener("DOMContentLoaded", function () {
  // 사이드 바 링크 활성화 비활성화 설정
  let links = document.querySelectorAll(".nav-link");
  let path = window.location.pathname.split("/")[2];
  let tableEl;

  for (let link of links) {
    if (link.dataset.path === path) {
      link.style.color = "#ebb917";
    } else {
      link.style.color = "#000";
    }
  }

  switch (path) {
    case "home":
      tableEl = renderDashboard();
      break;
    case "team":
      tableEl = renderTeam();
      break;
    case "user":
      tableEl = renderUser();
      break;
    case "study":
      tableEl = renderStudy();
      break;

    default:
      break;
  }

  // 필터 이벤트 리스너 추가
  fieldEl.addEventListener("change", () => updateFilter(tableEl));
  typeEl.addEventListener("change", () => updateFilter(tableEl));
  valueEl.addEventListener("keyup", () => updateFilter(tableEl));

  // 필터 초기화 버튼 이벤트 리스너 추가
  document
    .getElementById("filter-clear")
    .addEventListener("click", function () {
      fieldEl.value = "";
      typeEl.value = "=";
      valueEl.value = "";

      tableEl.clearFilter();
    });
});
