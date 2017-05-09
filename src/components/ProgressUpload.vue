<template>
  <div id="progress-upload" class="progress progress-striped active">
    <div class="progress-bar" role="progressbar" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100" :style="'width: '+uploadPercent+'%;'"></div>
  </div>
</template>

<script>
export default {
  name: 'progressUpload',
  props: ['props', 'url', 'course', 'index'],
  data: function () {
    return {
      chunkSize: (1024 * 100),
      rangeStart: 0,
      rangeEnd: (1024 * 100),
      fileSize: this.props.size,
      sliceMethod: 'slice',
      uploadRequest: null,
      uploadPercent: 0
    }
  },
  mounted: function () {
    this.chunkUpload()
  },
  methods: {
    chunkUpload() {
      if ('mozSlice' in this.props) {
        this.sliceMethod = 'mozSlice'
      } else if ('webkitSlice' in this.props) {
        this.sliceMethod = 'webkitSlice'
      } else {
        this.sliceMethod = 'slice'
      }

      this.uploadRequest = new XMLHttpRequest()
      this.uploadRequest.onload = this.upload()
    },
    upload() {
      var self = this
      var chunk

      if (this.rangeEnd > this.fileSize) {
        this.rangeEnd = this.fileSize
      }

      chunk = this.props[this.sliceMethod](this.rangeStart, this.rangeEnd)
      self.uploadRequest.open('PUT', this.url, true)
      self.uploadRequest.overrideMimeType('application/octet-stream')

      self.uploadRequest.setRequestHeader('Authorization', 'bearer ' + localStorage.getItem('at'))
      self.uploadRequest.setRequestHeader('Content-Lecture', this.course)
      self.uploadRequest.setRequestHeader('Content-Name', encodeURIComponent(this.props.name))
      self.uploadRequest.setRequestHeader('Content-Start', self.rangeStart)
      self.uploadRequest.setRequestHeader('Content-End', self.rangeEnd)
      self.uploadRequest.setRequestHeader('Content-FileSize', self.fileSize)
      self.uploadRequest.setRequestHeader('Content-Range', 'bytes ' + self.rangeStart + '-' + self.rangeEnd + '/' + self.fileSize)

      self.uploadRequest.send(chunk)
      self.uploadRequest.onreadystatechange = function () {
        if (self.uploadRequest.readyState === 4 && self.uploadRequest.status === 200) {
          self.onChunkComplete()
        }
      }
    },
    onChunkComplete() {
      var uploadPercent = (this.rangeEnd / this.fileSize) * 100
      this.$set(this, 'uploadPercent', uploadPercent.toFixed(2))

      if (this.rangeEnd === this.fileSize) {
        this.$parent.$emit('onCompleteUpload', this.index)
        return
      }

      this.rangeStart = this.rangeEnd
      this.rangeEnd = this.rangeEnd + this.chunkSize
      this.upload()
    }
  }
}
</script>

<style lang="scss">
#progress-upload {
  height: 20px;
}
</style>
