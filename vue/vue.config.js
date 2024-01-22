const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    host: '0.0.0.0',
    //public: '211.64.41.132',
    port: '13761',
    https: false,
    proxy: {
      '/api': {
        target: 'http://192.168.3.128:9090',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
})
