import flask
from flask import request
import os
import tensorflow as tf
from werkzeug.utils import secure_filename
from PIL import Image, ImageOps
import numpy as np
import shutil

model1 = None
model2 = None
model3 = None
model4 = None

def load_model():
    global model1
    global model2
    global model3
    global model4
    # model variable refers to the global variable
    model1 = tf.keras.models.load_model('saved_model/my_model1')
    model2 = tf.keras.models.load_model('saved_model/my_model2')
    model3 = tf.keras.models.load_model('saved_model/my_model3')
    model4 = tf.keras.models.load_model('saved_model/my_model4')

def test_predictor1(tmpname):
    # reading image
    img = Image.open(tmpname)
    im = img.convert('RGB')
    im_invert = ImageOps.invert(im)

    # converting image to greyscale
    greyscale_inverted_img = im_invert.convert("L")
    # resizing image to 28x28
    resized_greyscale_img = greyscale_inverted_img.resize((14,14))

    # converting Image object to numpy array
    img_array = np.asarray(resized_greyscale_img).astype(np.float32)
    
    # restructing from 28x28 to 28x28x1
    restructured_img_array = []
    for horz_pixel_iter in img_array.tolist():
        pxl_array = []
        for pxl in horz_pixel_iter:
            pxl_array.append([pxl])
        restructured_img_array.append(pxl_array)
    
    # making it as an array of images
    input_image_data = np.asarray([restructured_img_array])

    
    input_image_data1 = np.asarray([i[:, :].tolist() for i in input_image_data])

    confidence_array1 = model1.predict(input_image_data1).tolist()[0]
    return confidence_array1

def test_predictor2(tmpname):
    # reading image
    img = Image.open(tmpname)
    im = img.convert('RGB')
    im_invert = ImageOps.invert(im)

    # converting image to greyscale
    greyscale_inverted_img = im_invert.convert("L")
    # resizing image to 28x28
    resized_greyscale_img = greyscale_inverted_img.resize((14,14))

    # converting Image object to numpy array
    img_array = np.asarray(resized_greyscale_img).astype(np.float32)
    
    # restructing from 28x28 to 28x28x1
    restructured_img_array = []
    for horz_pixel_iter in img_array.tolist():
        pxl_array = []
        for pxl in horz_pixel_iter:
            pxl_array.append([pxl])
        restructured_img_array.append(pxl_array)
    
    # making it as an array of images
    input_image_data = np.asarray([restructured_img_array])

    
    input_image_data1 = np.asarray([i[:, :].tolist() for i in input_image_data])

    confidence_array1 = model2.predict(input_image_data1).tolist()[0]
    return confidence_array1

def test_predictor3(tmpname):
    # reading image
    img = Image.open(tmpname)
    im = img.convert('RGB')
    im_invert = ImageOps.invert(im)

    # converting image to greyscale
    greyscale_inverted_img = im_invert.convert("L")
    # resizing image to 28x28
    resized_greyscale_img = greyscale_inverted_img.resize((14,14))

    # converting Image object to numpy array
    img_array = np.asarray(resized_greyscale_img).astype(np.float32)
    
    # restructing from 28x28 to 28x28x1
    restructured_img_array = []
    for horz_pixel_iter in img_array.tolist():
        pxl_array = []
        for pxl in horz_pixel_iter:
            pxl_array.append([pxl])
        restructured_img_array.append(pxl_array)
    
    # making it as an array of images
    input_image_data = np.asarray([restructured_img_array])

    
    input_image_data1 = np.asarray([i[:, :].tolist() for i in input_image_data])

    confidence_array1 = model3.predict(input_image_data1).tolist()[0]
    return confidence_array1


def test_predictor4(tmpname):
    # reading image
    img = Image.open(tmpname)
    im = img.convert('RGB')
    im_invert = ImageOps.invert(im)

    # converting image to greyscale
    greyscale_inverted_img = im_invert.convert("L")
    # resizing image to 28x28
    resized_greyscale_img = greyscale_inverted_img.resize((14,14))

    # converting Image object to numpy array
    img_array = np.asarray(resized_greyscale_img).astype(np.float32)
    
    # restructing from 28x28 to 28x28x1
    restructured_img_array = []
    for horz_pixel_iter in img_array.tolist():
        pxl_array = []
        for pxl in horz_pixel_iter:
            pxl_array.append([pxl])
        restructured_img_array.append(pxl_array)
    
    # making it as an array of images
    input_image_data = np.asarray([restructured_img_array])

    
    input_image_data1 = np.asarray([i[:, :].tolist() for i in input_image_data])

    confidence_array1 = model4.predict(input_image_data1).tolist()[0]
    return confidence_array1


app = flask.Flask(__name__)

@app.route("/upload1", methods=["POST"])
def upload_image1():
    if request.method == 'POST':
        if 'file' not in request.files:
            return 'No file attached in request'
        f = request.files['file']
        if f.filename == '':
            return 'No file selected'
        
        # saving image in a temporary folder
        temp_folder = "temp"
        tmpname = temp_folder+'/'+str(secure_filename(f.filename))
        if not os.path.exists(temp_folder):
            os.makedirs(temp_folder)
        while not os.path.isfile(tmpname):
            f.save(tmpname)

        confidence_array1 = test_predictor1(tmpname)
        confidence_array1 = ",".join([str(i) for i in confidence_array1])
        return confidence_array1

@app.route("/upload2", methods=["POST"])
def upload_image2():
    if request.method == 'POST':
        if 'file' not in request.files:
            return 'No file attached in request'
        f = request.files['file']
        if f.filename == '':
            return 'No file selected'
        
        # saving image in a temporary folder
        temp_folder = "temp"
        tmpname = temp_folder+'/'+str(secure_filename(f.filename))
        if not os.path.exists(temp_folder):
            os.makedirs(temp_folder)
        while not os.path.isfile(tmpname):
            f.save(tmpname)

        confidence_array1 = test_predictor2(tmpname)
        confidence_array1 = ",".join([str(i) for i in confidence_array1])
        return confidence_array1

@app.route("/upload3", methods=["POST"])
def upload_image3():
    if request.method == 'POST':
        if 'file' not in request.files:
            return 'No file attached in request'
        f = request.files['file']
        if f.filename == '':
            return 'No file selected'
        
        # saving image in a temporary folder
        temp_folder = "temp"
        tmpname = temp_folder+'/'+str(secure_filename(f.filename))
        if not os.path.exists(temp_folder):
            os.makedirs(temp_folder)
        while not os.path.isfile(tmpname):
            f.save(tmpname)

        confidence_array1 = test_predictor3(tmpname)
        confidence_array1 = ",".join([str(i) for i in confidence_array1])
        return confidence_array1

@app.route("/upload4", methods=["POST"])
def upload_image4():
    if request.method == 'POST':
        if 'file' not in request.files:
            return 'No file attached in request'
        f = request.files['file']
        if f.filename == '':
            return 'No file selected'
        
        # saving image in a temporary folder
        temp_folder = "temp"
        tmpname = temp_folder+'/'+str(secure_filename(f.filename))
        if not os.path.exists(temp_folder):
            os.makedirs(temp_folder)
        while not os.path.isfile(tmpname):
            f.save(tmpname)

        confidence_array1 = test_predictor4(tmpname)
        confidence_array1 = ",".join([str(i) for i in confidence_array1])
        return confidence_array1

if __name__ == "__main__":
    load_model()
    app.run(host="0.0.0.0",debug=True)