# Stage 1: Build
FROM node:18-alpine AS build

WORKDIR /app

# Copy package files and install dependencies (leverage Docker cache)
COPY package*.json ./
RUN npm ci --silent

# Copy source code and build
COPY . .
RUN npm run build

# Stage 2: Serve with nginx
FROM nginx:alpine

# Copy build artifacts to nginx
COPY --from=build /app/build /usr/share/nginx/html

# Copy nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"]