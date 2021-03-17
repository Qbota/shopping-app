FROM node:lts-alpine

# install simple http server for serving static content
RUN npm install -g http-server

# make the 'app' folder the current working directory
WORKDIR /app

# copy both 'package.json' and 'package-lock.json' (if available)
COPY package*.json ./

# install project dependencies
RUN yarn install

# copy project files and folders to the current working directory (i.e. 'app' folder)
COPY . .

RUN sed -i 's/localhost:8080/140.82.37.246:8080/g' src/config.js

# build app for production with minification
RUN yarn build

EXPOSE 4200
CMD [ "http-server", "dist", "-p4200" ]