<template>
    <div>
        <el-container>
            <el-divider class="m-divider"><i class="el-icon-arrow-up"></i></el-divider>
            <el-footer>
                # this is a simple blog system written by springboot and vue, thanks for
                <a href="https://space.bilibili.com/13491144" target="_blank">MarkerHub teacher</a>
                 and <a href="https://github.com/lenve/VBlog" target="_blank">VBlog</a>
            </el-footer>
        </el-container>
    </div>
</template>

<script>
    export default {
        name: "Footer"
    }
</script>

<style scoped>
    /*底栏样式*/
    .el-footer {
        color: #333;
        text-align: center;
        line-height: 65px;
        font-family: 'Dubai Light';
        font-size: 18px;
        margin-top: 100px;
    }
    /*分割线样式*/
    .m-divider{
        margin-top: 100px;
        margin-bottom: -100px;
    }
</style>
