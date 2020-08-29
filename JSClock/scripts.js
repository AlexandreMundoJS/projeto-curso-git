window.onload = start;
function start() {
    function showAnalogicTime() {
        /* Analogic Clock */
        const HOURHAND = document.querySelector("#hour");
        const MINUTEHAND = document.querySelector("#minute");
        const SECONDHAND = document.querySelector("#second");
        /* Use in analogic and Digital */
        let date = new Date();
        let horas = date.getHours();
        let minutos = date.getMinutes();
        let segundos = date.getSeconds();
        /* Analogic pointers */
        let hrPosition = horas * 360 / 12 + ((minutos * 360 / 60) / 12);
        let minPosition = (minutos * 360 / 60) + (segundos * 360 / 60) / 60;
        let secPosition = segundos * 360 / 60;

        // Change analogic pointer styles
        HOURHAND.style.transform = "rotate(" + hrPosition + "deg)";
        MINUTEHAND.style.transform = "rotate(" + minPosition + "deg)";
        SECONDHAND.style.transform = "rotate(" + secPosition + "deg)";
        setTimeout(showAnalogicTime, 1000);
    }
    showAnalogicTime();

    function showDigitalTime() {

        /* Use in analogic and Digital */
        let date = new Date();
        let horas = date.getHours();
        let minutos = date.getMinutes();
        let segundos = date.getSeconds();
        let session = "AM";


        if (horas == 0) {
            horas = 12;
        }
        if (horas > 0) {
            horas = horas - 12;
            session = "PM";
        }

        horas = (horas < 10) ? "0" + horas : horas;
        minutos = (minutos < 10) ? "0" + minutos : minutos;
        segundos = (segundos < 10) ? "0" + segundos : segundos;



        let time = horas + ":" + minutos + ":" + segundos + " " + session;
        document.getElementById("myDigitalClockDisplay").innerText = time;
        document.getElementById("myDigitalClockDisplay").textContent = time;
        setTimeout(showDigitalTime, 1000);
    }
    showDigitalTime();
}





