const express = require('express');
const app = express();
const multer = require('multer');
const fs = require('fs');
const Predict = require('./Predict');

//Test predict fuction
//const image = '/home/c163dsx1659/models API/uploads/IMG_20230528_190555_1.jpg';
//async function main() {
//    const result = await Predict(image);
//    console.log(result);
//}
//main();

// Import the disk storage engine
const storage = multer.diskStorage({
  // Specify the destination folder
  destination: function (req, file, cb) {
    cb(null, 'uploads/')
  },
  // Specify the filename
  filename: function (req, file, cb) {
    cb(null, file.originalname)
  }
})

// Create an upload instance with the storage option
const upload = multer({ storage: storage })

app.use(express.static('uploads/'));

// Define a POST endpoint for /predict with upload.single middleware
app.post('/predict', upload.single('image'), async (req, res) => {
  try {
    // Get the input image from the request file
    const image = req.file.path;
    const result = await Predict(image);
    res.json(result);
    console.log(result);

    fs.unlink(image, (err) => { // delete image file
        if (err) throw err;
        console.log("Image deleted");

    });

  } catch (error) {
    console.error(error);
    res.status(500).send('Something went wrong');
  }
});

app.get("/", (req, res) => {
    console.log("Response success")
    res.send("Response Success!")
})

const PORT = process.env.PORT || 8000
app.listen(PORT, () => {
    console.log("Server is up and listening on " + PORT)
})