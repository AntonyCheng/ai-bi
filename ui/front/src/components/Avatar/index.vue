<template>
  <div class="default-avater" :style="avaterSize">
    <span class="avater-name" :style="fontSize">{{ name }}</span>
  </div>
</template>

<script>
export default {
  name: 'Avatar',
  props: {
    avaterName: {
      // 标识emit
      require: true,
      type: String,
      default: 'A'
    },
    width: {
      // 标识emit
      require: true,
      type: String,
      default: '40px'
    },
    height: {
      // 标识emit
      require: true,
      type: String,
      default: '40px'
    }
  },
  data() {
    return {
      name: '-'
    }
  },
  computed: {
    avaterSize() {
      return `width:${this.width};height:${this.height}`
    },
    fontSize() {
      // console.log(this.width.split("px")[0]);
      const styleFont = `fontSize:${this.width.split('px')[0] / 2}px;`
      return styleFont
    }
  },
  mounted() {
    this.name = this.avaterName[0]
  }
}
</script>

<style lang="scss">
.default-avater {
  // min-width: 40px;
  // height: 40px;
  background: #aaaaaa;
  border-radius: 50%;
  // line-height: 40px;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor:pointer;

  // 默认头像
  .avater-name {
    font-family: "微软雅黑", arial;
    // font-size: 20px;
    font-weight: 700;
  }
}
</style>
