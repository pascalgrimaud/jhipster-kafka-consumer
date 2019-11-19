<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="jsonMessage">
                <h2 class="jh-entity-heading"><span>JsonMessage</span> {{jsonMessage.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span>Field 1</span>
                    </dt>
                    <dd>
                        <span>{{jsonMessage.field1}}</span>
                    </dd>
                    <dt>
                        <span>Field 2</span>
                    </dt>
                    <dd>
                        <span>{{jsonMessage.field2}}</span>
                    </dd>
                    <dt>
                        <span>Number</span>
                    </dt>
                    <dd>
                        <span>{{jsonMessage.number}}</span>
                    </dd>
                    <dt>
                        <span>Received At</span>
                    </dt>
                    <dd>
                        <span>{{jsonMessage.receivedAt | formatDate}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
                </button>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./json-message-details.component.ts">
</script>
