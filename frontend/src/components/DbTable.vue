<template>
    <div>
        <el-table
                :data="tableData"
                border
                style="width: 100%"
                class="table">
            <el-table-column
                    fixed
                    prop="id"
                    label="id"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="originalid"
                    label="originalid"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="alias"
                    label="alias"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="name"
                    width="130">
            </el-table-column>
            <el-table-column
                    prop="firstName"
                    label="firstName"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="secondName"
                    label="secondName"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="lastName"
                    label="lastName"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="salt"
                    label="salt"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="refined"
                    label="refined"
                    width="130">
            </el-table-column>
            <el-table-column
                    prop="sortNo"
                    label="sortNo"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="hidden"
                    label="hidden"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="password"
                    label="password"
                    width="100">
            </el-table-column>
            <el-table-column
                  prop="createDate"
                  label="createDate"
                  width="300"
                  :formatter="formatter">
            </el-table-column>
            <el-table-column
                  fixed="right"
                  label="Operation"
                  width="100">
                <template scope="scope">
                    <el-button @click="editItem(scope.$index, tableData)" type="text" size="large">Редактировать</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination class="pagination" layout="prev, pager, next" :total="total" :page-size="pageSize"
                       v-on:current-change="changePage">
        </el-pagination>
        <db-modal :dialogFormVisible="dialogFormVisible" :form="form" v-on:canclemodal="dialogVisible"></db-modal>
    </div>

</template>

<script>
    import Bus from '../eventBus'
    import DbModal from './DbModal.vue'

    export default {
        data(){
            return {
                tableData: [],
                apiUrl: 'http://127.0.0.1:8000/api/employee',
                total: 0,
                pageSize: 10,
                currentPage: 1,
                firstName: '',
                secondName: '',
                dialogFormVisible: false,
                form: '',
            }
        },
        components: {
            DbModal
        },
        mounted () {
            this.getCustomers();
            Bus.$on('filterResultData', (data) => {
                this.tableData = data.results;
                this.total = data.total_pages;
                this.pageSize = data.count;
                this.secondName = data.secondName;
                this.firstName = data.firstName;

            });
        },

        methods: {

            dialogVisible: function () {
                this.dialogFormVisible = false;
            },

            getCustomers: function () {
                this.$axios.get(this.apiUrl, {
                    params: {
                        page: this.currentPage,
                        firstName: this.firstName,
                        secondName: this.secondName
                    }
                }).then((response) => {
                    this.tableData = response.data.data.results;
                    this.total = response.data.data.total;
                    this.pageSize = response.data.data.count;
                    console.log(response.data.data);
                }).catch(function (response) {
                    console.log(response)
                });
            },
            changePage: function (currentPage) {
                this.currentPage = currentPage;
                this.getCustomers()
            },
            editItem: function (index, rows) {
                this.dialogFormVisible = true;
                const itemId = rows[index].id;
                const idurl = 'http://127.0.0.1:8000/api/employee/detail/' + itemId;
                this.$axios.get(idurl).then((response) => {
                    this.form = response.data;
                }).catch(function (response) {
                    console.log(response)
                });
            },

            formatter(row, column) {
                let data = this.$moment(row.createDate, this.$moment.ISO_8601);
                return data.format('YYYY-MM-DD')
            },
        }
    }
</script>

<style>
    .table {
        margin-top: 30px;
    }

    .pagination {
        margin-top: 10px;
        float: right;
    }

</style>
