document.addEventListener("DOMContentLoaded", function () {
    const calendarElement = document.getElementById("calendar");

    const completedDates = document.querySelectorAll(".workout-date");

    const events = Array.from(completedDates).map(function (dateElement) {
        return {
            id: dateElement.dataset.id,
            title: "Workout completed",
            start: dateElement.dataset.date,
            allDay: true
        };
    });

    const calendar = new FullCalendar.Calendar(calendarElement, {
        initialView: "dayGridMonth",
        height: "auto",
        events: events,
        eventClick: function (info) {
            const deleteForm = document.querySelector(
                '.delete-workout-form[data-log-id="' + info.event.id + '"]'
            );

            if (deleteForm && window.confirm("Remove this completed workout?")) {
                deleteForm.requestSubmit();
            }
        },
        headerToolbar: {
            left: "prev,next today",
            center: "title",
            right: ""
        }
    });

    calendar.render();
});