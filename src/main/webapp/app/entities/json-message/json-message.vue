<template>
    <div>
        <h2 id="page-heading">
            <span id="json-message-heading">Json Messages</span>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && jsonMessages && jsonMessages.length === 0">
            <span>No jsonMessages found</span>
        </div>
        <div class="table-responsive" v-if="jsonMessages && jsonMessages.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span>ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('field1')"><span>Field 1</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('field2')"><span>Field 2</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('number')"><span>Number</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('receivedAt')"><span>Received At</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="jsonMessage in jsonMessages"
                    :key="jsonMessage.id">
                    <td>
                        <router-link :to="{name: 'JsonMessageView', params: {jsonMessageId: jsonMessage.id}}">{{jsonMessage.id}}</router-link>
                    </td>
                    <td>{{jsonMessage.field1}}</td>
                    <td>{{jsonMessage.field2}}</td>
                    <td>{{jsonMessage.number}}</td>
                    <td>{{jsonMessage.receivedAt | formatDate}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'JsonMessageView', params: {jsonMessageId: jsonMessage.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline">View</span>
                            </router-link>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="consumerApp.jsonMessage.delete.question">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-jsonMessage-heading" >Are you sure you want to delete this Json Message?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-jsonMessage" v-on:click="removeJsonMessage()">Delete</button>
            </div>
        </b-modal>
        <div v-show="jsonMessages && jsonMessages.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./json-message.component.ts">
</script>
