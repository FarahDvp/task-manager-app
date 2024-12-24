#FROM node:16-alpine AS build
#WORKDIR /app
#COPY . .
#RUN npm install --legacy-peer-deps && \
#    npm run build --base-href=app
#FROM nginx:alpine
#EXPOSE 4200
#COPY --from=build /app/dist/* /usr/share/nginx/html/
#COPY /nginx.conf /etc/nginx/conf.d/default.conf

FROM node:16-alpine AS build

WORKDIR /home/farah

COPY . .

RUN npm install --legacy-peer-deps && \
    npm run build --base-href=app

FROM nginx:alpine

EXPOSE 80

WORKDIR /usr/share/nginx/html/app

COPY --from=build /home/farah/dist/* .

COPY nginx.conf /etc/nginx/conf.d/default.conf

