import { AppRegistry } from 'react-native';
import App from './App';
import widgetTask from './widgetTask';

AppRegistry.registerComponent('androidWidgetPoc', () => App);
AppRegistry.registerHeadlessTask('widgetTask', () => widgetTask);

