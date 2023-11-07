document.addEventListener("DOMContentLoaded", () => {
    document.title = "Karate API Automation Report";
    document.querySelector("#navigation > div > div > p").textContent = "Karate API Test Report";
    document.getElementById("footer").remove();
    document.querySelector("#navigation > div > div > ul > li:nth-child(3)").remove();
    document.querySelector(".carousel-inner>div:nth-child(3)").remove();
    document.querySelector(".carousel-indicators>li:nth-child(3)").remove();
});