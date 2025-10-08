"use strict";
// IMPORTANT: You need to replace this placeholder!
// 1. Run your Android app and take a screenshot of the edge-detected view.
// 2. Use an online tool (like "base64-image.de") to convert your screenshot to a Base64 string.
// 4. Paste that long string here.
const sampleFrameBase64 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=";
function displayFrame() {
    const frameElement = document.getElementById('processed-frame');
    const statsElement = document.getElementById('stats');
    if (frameElement && statsElement) {
        frameElement.src = sampleFrameBase64;
        statsElement.innerText = `Resolution: 640x480 (sample) | FPS: 15 (mock)`;
    }
}
document.addEventListener('DOMContentLoaded', displayFrame);
