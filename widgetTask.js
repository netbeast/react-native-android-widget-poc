/**
* @flow
*/

import { NativeModules, ToastAndroid } from 'react-native'

const { BackgroundTaskBridge } = NativeModules

const charms = [
  {
    id: 'uuid1',
    name: 'First',
    cover: 'goodmorning',
  },
  {
    id: 'uuid2',
    name: 'Second',
    cover: 'night',
  }
]

type TaskInfo = {
  id: string,
}
export default function widgetTask (taskData: TaskInfo) {
  synchronizeWidget()
  if (taskData && taskData.id) {
    triggerCharm(taskData.id)
  }

  return new Promise(resolve => {
    setTimeout(() => {
        ToastAndroid.show(`FINISHING 🚬 ...`, ToastAndroid.SHORT);
        synchronizeWidget()
        resolve()
    }, 8000) 
  })
}

export function synchronizeWidget () {
  ToastAndroid.show(`Initializing ...`, ToastAndroid.SHORT);
  BackgroundTaskBridge.initializeWidgetBridge(charms)
}

function triggerCharm (id) {
  if (!id) return
  ToastAndroid.show(`Triggering ${id}...`, ToastAndroid.SHORT);
}