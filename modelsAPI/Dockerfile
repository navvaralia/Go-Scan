# Pull in the official lightweight version of Node 12.
FROM node:18.12.1

# Create and change to the app directory.
WORKDIR /app

# Copy package.json and package-lock.json files
COPY package.json .
COPY package-lock.json .

# Install production dependencies.
RUN npm install express multer @tensorflow/tfjs-node fs path @google-cloud/debug-agent

# Copy local codebase into the container image
COPY . .

# Start the api server
CMD [ "npm", "start" ]