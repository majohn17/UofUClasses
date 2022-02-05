// bgImg is the background image to be modified.
// fgImg is the foreground image.
// fgOpac is the opacity of the foreground image.
// fgPos is the position of the foreground image in pixels. It can be negative and (0,0) means the top-left pixels of the foreground and background are aligned.
function composite(bgImg, fgImg, fgOpac, fgPos)
{
    var bgIndex = 0;
    var xUpper = fgPos.x + fgImg.width;
    var yUpper = fgPos.y + fgImg.height - 1;

    var fgLowerX = 0;
    if (fgImg.width == bgImg.width)
        var fgUpperX = fgImg.width - 1;
    else
        var fgUpperX = fgImg.width;
    var fgLowerY = 0;
    var fgUpperY = fgImg.height;
    if (fgPos.x > bgImg.width || xUpper < 0 || fgPos.y > bgImg.height || yUpper < 0) {
        /* Do Nothing */
    }
    else {
        if (fgPos.x < 0)
            fgLowerX = Math.abs(fgPos.x);
        if (xUpper > bgImg.width)
            fgUpperX = fgImg.width - (xUpper - bgImg.width + 1);
        if (fgPos.y < 0)
            fgLowerY = Math.abs(fgPos.y);
        if (yUpper > bgImg.height)
            fgUpperY = fgImg.height - (yUpper - bgImg.height);
    }

    if (fgPos.x < 0) {
        if (fgPos.y < 0)
            var fgIndex = fgLowerX + (fgImg.width * fgLowerY);
        else
            var fgIndex = fgLowerX;
    }
    else {
        if (fgPos.y < 0)
            var fgIndex = fgImg.width * fgLowerY;
        else
            var fgIndex = 0;
    }

    var fgWidth = 0;
    if (fgPos.x < 0) 
        fgWidth = xUpper;
    else
        fgWidth = fgUpperX;
    var fgCount = 0;

    for (i = 0; i < bgImg.height; i++) {
        for (j = 0; j < bgImg.width; j++) {
            if (i >= fgPos.y && i <= yUpper && j >= fgPos.x && j <= xUpper) {
                var fAlpha = (fgImg.data[fgIndex * 4 + 3] * fgOpac) / 255;
                var bAlpha = bgImg.data[bgIndex * 4 + 3] / 255;
                var aEquation = fAlpha + ((1 - fAlpha) * bAlpha);
                if (aEquation == 0) {
                    bgImg.data[bgIndex * 4] = 0;
                    bgImg.data[bgIndex * 4 + 1] = 0;
                    bgImg.data[bgIndex * 4 + 2] = 0;
                    bgImg.data[bgIndex * 4 + 3] = 0;
                }
                else {
                    bgImg.data[bgIndex * 4] = ((fAlpha * fgImg.data[fgIndex * 4]) + ((1 - fAlpha) * bgImg.data[bgIndex * 4] * bAlpha)) / aEquation;
                    bgImg.data[bgIndex * 4 + 1] = ((fAlpha * fgImg.data[fgIndex * 4 + 1]) + ((1 - fAlpha) * bgImg.data[bgIndex * 4 + 1] * bAlpha)) / aEquation;
                    bgImg.data[bgIndex * 4 + 2] = ((fAlpha * fgImg.data[fgIndex * 4 + 2]) + ((1 - fAlpha) * bgImg.data[bgIndex * 4 + 2] * bAlpha)) / aEquation;
                    bgImg.data[bgIndex * 4 + 3] = aEquation * 255
                }

                fgCount++;
                if (fgCount > fgWidth) {
                    fgCount = 0;
                    fgIndex += (fgImg.width - fgWidth);
                }
                else
                    fgIndex++;
            }
            else  {
                var bAlpha = bgImg.data[bgIndex * 4 + 3] / 255;
                var aEquation = bAlpha;
                if (aEquation == 0) {
                    bgImg.data[bgIndex * 4] = 0;
                    bgImg.data[bgIndex * 4 + 1] = 0;
                    bgImg.data[bgIndex * 4 + 2] = 0;
                    bgImg.data[bgIndex * 4 + 3] = 0;
                }
                else {
                    bgImg.data[bgIndex * 4] = ((bgImg.data[bgIndex * 4] * bAlpha)) / aEquation;
                    bgImg.data[bgIndex * 4 + 1] = ((bgImg.data[bgIndex * 4 + 1] * bAlpha)) / aEquation;
                    bgImg.data[bgIndex * 4 + 2] = ((bgImg.data[bgIndex * 4 + 2] * bAlpha)) / aEquation;
                    bgImg.data[bgIndex * 4 + 3] = aEquation * 255
                }
            }
            bgIndex++;  
        }
    }
}
