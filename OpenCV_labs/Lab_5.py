import cv2 as cv

def process(path, thrashLow, thrashAr, kernSize, sigma):
    cap = cv.VideoCapture(path, cv.CAP_ANY)

    width = int(cap.get(cv.CAP_PROP_FRAME_WIDTH))
    height = int(cap.get(cv.CAP_PROP_FRAME_HEIGHT))

    fourcc = cv.VideoWriter_fourcc(*'mp4v')
    video_writer = cv.VideoWriter("output.mp4", fourcc, 20, (width, height))
    ret, frame = cap.read()


    gray = cv.cvtColor(frame, cv.COLOR_BGR2GRAY)
    pred_frame = cv.GaussianBlur(gray, (kernSize, kernSize), sigma)

    while True:
        ret, frame = cap.read()
        if not (ret):
            break

        gray_frame = cv.cvtColor(frame, cv.COLOR_BGR2GRAY)
        gauss = cv.GaussianBlur(gray_frame, (kernSize, kernSize), sigma)
        frame_diff = cv.absdiff(pred_frame, gauss)
        thrash = cv.threshold(frame_diff, thrashLow, 255, cv.THRESH_BINARY)[1]
        contours, _ = cv.findContours(thrash, cv.RETR_EXTERNAL, cv.CHAIN_APPROX_SIMPLE)

        try:
            cnt = contours[0]
            area = cv.contourArea(cnt)
            if area > thrashAr:
                video_writer.write(frame)
        except:
            print("Нет контура")
        pred_frame = gauss

    cap.release()
    cv.destroyAllWindows()


process("Media_Ind_lab/video7.mp4", 200, 30, 3, 100)