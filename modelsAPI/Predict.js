const tf = require('@tensorflow/tfjs-node');
const fs = require('fs');

// Define the path to the model and image files
const modelPath = 'https://storage.googleapis.com/go-scan-models/Model%20MNV%20V2/model.json';

// Load the model from a file
async function loadModel() {
  try {
    const model = await tf.loadLayersModel(modelPath);
    return model;
  } catch (error) {
    console.error(error);
  }
}

// Run inference on the input tensor and get the output tensor
async function Predict(image) {
  try {
    const model = await loadModel();

    const imageBuffer = fs.readFileSync(image);
    const input = tf.node.decodeImage(imageBuffer);
    const resized = tf.image.resizeBilinear(input, [224, 224]);
    const normalized = resized.div(127.5).sub(1);
    const batched = normalized.expandDims(0);
    const output = model.predict(batched);

    //console.log(output.shape);
    //console.log(output.dataSync());

    const tensors = tf.tensor(output.dataSync());

    const product_list = [
     "Aqua 300ml", //0
     "Bearbrand", //1
     "Chitato", //2
     "Garnier Acno Fight", //3
     "Garnier Turbo Bright", //4
     "Indomie Ayam Spesial", //5
     "Mi Goreng", //6
     "Pepsodent", //7
     "Rexona Antibacterial", //8
     "Silverqueen", //9
     "Sunlight" //10
    ];

    const price_list = {
        "Aqua 300ml":"3000", //0
        "Bearbrand":"10000", //1
        "Chitato":"9000", //2
        "Garnier Acno Fight":"40000", //3
        "Garnier Turbo Bright":"40000", //4
        "Indomie Ayam Spesial":"3500", //5
        "Mi Goreng":"3500", //6
        "Pepsodent":"15000", //7
        "Rexona Antibacterial":"27000", //8
        "Silverqueen":"18000", //9
        "Sunlight":"5000" //10
    };
    
    const index = tensors.argMax();
    //output label
    const name = product_list[index.dataSync()];
    const price = price_list[product_list[index.dataSync()]];
    return{"name" : name,
        "price" : price
    } 
  } catch (error) {
    console.error(error);
  }
}

module.exports = Predict;
