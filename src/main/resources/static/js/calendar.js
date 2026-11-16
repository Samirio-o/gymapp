document.addEventListener("DOMContentLoaded", function () {
    const calendarElement = document.getElementById("calendar");

    // Reads every completed workout date rendered by Thymeleaf.
    const completedDates = document.querySelectorAll(".workout-date");

    const events = Array.from(completedDates).map(function (dateElement) {
        return {
            title: "Workout completed",
            start: dateElement.dataset.date,
            allDay: true
        };
    });

    // Creates the interactive FullCalendar instance.
    const calendar = new FullCalendar.Calendar(calendarElement, {
        initialView: "dayGridMonth",
        height: "auto",
        events: events,
        headerToolbar: {
            left: "prev,next today",
            center: "title",
            right: ""
        }
    });

    calendar.render();
});