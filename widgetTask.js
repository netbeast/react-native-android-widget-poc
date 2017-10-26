/**
* @flow
*/

import { NativeModules, ToastAndroid } from 'react-native'
const { BackgroundTaskBridge } = NativeModules

type TaskInfo = {
  id: string,
}
export default async function widgetTask (taskData: TaskInfo) {
  const {id} = taskData || {}
  synchronizeWidget()
  triggerCharm(id)
}

export function synchronizeWidget () {
  BackgroundTaskBridge.initializeWidgetBridge(charms)
}

function triggerCharm (id) {
  ToastAndroid.show(`Triggering ${id}...`, ToastAndroid.SHORT);
}

const charms = {
  uuid1: {
    id: 'uuid1',
    name: 'First',
  },
  uuid2: {
    id: 'uuid2',
    name: 'Second',
  }
}
