# Distributed_Digit_Recognition_Mobile_Edge_Collaboration

This project demonstrates a distributed system for recognizing handwritten digits using machine learning (CNN) models. The system splits an input digit image into quadrants, sends them to different client devices for inference, and aggregates the results to make a final prediction.

## 📁 Project Structure
```
Distributed_Digit_Recognition_Mobile_Edge_Collaboration/
├── report.pdf                  # Project report
├── MLApp/                      # Android app source code
├── saved_model/               # Pre-trained models for each client
├── scripts/                   # Python scripts for training and prediction
└── README.md                  # Project documentation
```

## 🚀 Features
- Distributed processing of digit image using 4 client devices
- CNN-based prediction on each client
- Aggregated prediction confidence on host
- Android app interface to manage image capture and transmission

## 🔧 Setup Instructions
### 1. Clone the repository
```bash
git clone https://github.com/Aquazero8/Distributed_Digit_Recognition.git
cd Distributed_Digit_Recognition
```

### 2. Python Environment Setup
```bash
cd scripts
pip install tensorflow numpy opencv-python flask
```

### 3. Start Flask Servers (on each client)
Update `predict.py` on each client with correct model path and run:
```bash
python predict.py
```
Each client will serve a quadrant for prediction.

### 4. Android App Setup
1. Open the `MLApp/` folder in Android Studio
2. Connect a device or use an emulator
3. Update client IP addresses in the app’s code
4. Run the app and test prediction

## 🧪 Training Custom Models (Optional)
You can retrain models using the training scripts:
```bash
python training1.py  # and so on for training2.py, etc.
```
Trained models are saved under `saved_model/`.

## 🛠 How It Works
- The Android app captures a digit image
- Splits it into 4 quadrants
- Sends each to a client via HTTP
- Clients respond with confidence arrays
- Host aggregates the confidences and returns final prediction

## 📌 Future Work
- Extend to image classification tasks (e.g., object detection)
- Real-time edge deployment on IoT devices
- Enhanced UI/UX and prediction feedback



