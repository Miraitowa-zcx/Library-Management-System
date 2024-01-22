<template>
  <div id="panel">
  </div>
</template>

<script>
export default {
  name: "mouseMove",
  methods: {
    mouseMove(e) {
      let oPanel = document.getElementById('panel')
      let oSpan = document.createElement('span')

      e = e || window.Event

      oSpan.style.left = e.clientX + 'px'
      oSpan.style.top = e.clientY - 50 +  'px'

      let size = Math.random() * 20

      oSpan.style.width = 5 + size + 'px'
      oSpan.style.height = 5 + size + 'px'

      oPanel.appendChild(oSpan)

      setTimeout(() => {
        oSpan.remove()
      }, 1000
      )
    }
  },
  mounted() {
    document.addEventListener('mousemove', this.mouseMove)
  },
  beforeDestroy() {
    document.removeEventListener('mousemove', this.mouseMove)
  }
}
</script>

<style lang='less'>
#panel {
  height: 100vh;
  width: 100%;
  position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: -1;
    span {
        width: 10px;
        height: 10px;
        background: #55b9f3;
        border-radius: 50%;
        position: absolute;
        box-shadow: 5px 5px 15px #489dcf, -5px -5px 15px #62d5ff;
        animation: blow 4s linear infinite;
        -webkit-animation: blow 4s linear infinite;
    }
}

@keyframes blow {
    0% {
      transform: translate(-50%, -50%);
      opacity: 1;
      filter: hue-rotate(0deg);
      -webkit-transform: translate(-50%, -50%);
      -moz-transform: translate(-50%, -50%);
      -ms-transform: translate(-50%, -50%);
      -o-transform: translate(-50%, -50%);
    }

  100% {
    transform: translate(-50%, -1000%);
    opacity: 0;
    filter: hue-rotate(720deg);
    webkit-transform: translate(-50%, -1000%);
    -moz-transform: translate(-50%, -1000);
    -ms-transform: translate(-50%, -1000%);
    -o-transform: translate(-50%, -1000%);
    -webkit-filter: hue-rotate(720deg);
  }
}
</style>
